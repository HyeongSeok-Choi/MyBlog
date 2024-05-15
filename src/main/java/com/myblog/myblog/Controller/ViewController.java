package com.myblog.myblog.Controller;


import com.myblog.myblog.Dto.*;
import com.myblog.myblog.Entity.Portfolio;
import com.myblog.myblog.Entity.User;
import com.myblog.myblog.Entity.guestBook;
import com.myblog.myblog.Service.PortpolioService;
import com.myblog.myblog.Service.UserService;
import com.myblog.myblog.Service.guestBookService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

import static org.aspectj.bridge.MessageUtil.error;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final guestBookService service;
    private final UserService userService;
    private final PortpolioService portpolioService;
    //회원가입 프로필 이미지 경로
    private final String uploadProfileDir = Paths.get("C:", "MemberProfileImg", "upload").toString();
    //포트폴리오 이미지 경로
    private final String uploadPortpolioDir = Paths.get("C:", "PortpolioImg", "upload").toString();


    @GetMapping(value = "/guestBook")
    public String guestBook(Model model, @RequestParam Long id) {

        System.out.println(id+"아이디 들어옴");
        guestBookViewDTO guestBookViewdto = service.getPortpolio(id);

        model.addAttribute("guestBook", guestBookViewdto);

            return "guestBook";
    }

    @GetMapping(value = "/guestbookList")
    public String guestbook(Model model, @PageableDefault(page=0,size = 10,sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String title) {

        Page<guestBook> guestBooks ;

        if(title == null){
            guestBooks = service.findAll(pageable);

        }else{
            guestBooks = service.findAllByTitle(title,pageable);
        }


        int nowPage = guestBooks.getPageable().getPageNumber()+1;
        int startPage=Math.max(nowPage-4,1);
        int endPage=Math.min(nowPage+5,guestBooks.getTotalPages());



        if(!guestBooks.isEmpty()) {
            model.addAttribute("guestBooks", guestBooks);
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            return "guestBookList";
        }

        model.addAttribute("nowPage", null);
        model.addAttribute("startPage", null);
        model.addAttribute("endPage", null);



        return "guestBookList";
    }

    //메인페이지 호출
    @GetMapping(value="/main")
    public String ViewMain(Model model) {


           PortpolioViewDTO portpolioViewDTO= portpolioService.getMainPortpolio();

            model.addAttribute("MainPortpolio",portpolioViewDTO);


        return "main";
    }

    //새글 등록 페이지 호출
    @PostMapping(value="/newcontent")
    public String CreateNewContent() {

        return "newcontent";
    }

    @GetMapping(value = {"/portpolioList"})
    public String portpolioList(Model model, @PageableDefault(page=0,size = 3,sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String title) {

        Page<Portfolio> portpolios ;

        if(title == null){
            portpolios = portpolioService.findAll(pageable);

        }else{
            portpolios = portpolioService.findAllByTitle(title,pageable);
        }


        int nowPage = portpolios.getPageable().getPageNumber()+1;
        int startPage=Math.max(nowPage-4,1);
        int endPage=Math.min(nowPage+5,portpolios.getTotalPages());



        if(!portpolios.isEmpty()) {
        model.addAttribute("portpolios", portpolios);
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            return "portpolioList";
        }

        model.addAttribute("nowPage", null);
        model.addAttribute("startPage", null);
        model.addAttribute("endPage", null);

        return "portpolioList";
    }


    //새 포트폴리오 등록 페이지 호출
    @PostMapping(value="/addportpolio")
    public String CreateNewPortpolio(Model model, Principal principal) {

        PortpolioFormDTO newPp = new PortpolioFormDTO();

        if(principal != null) {
            principal.getName();

            User user = userService.findByEmail(principal.getName());

            newPp.setEmail(user.getEmail());
            newPp.setPhoneNumber(user.getPhoneNumber());
            newPp.setName(user.getRealUserName());


        }


        model.addAttribute("PortpolioFormDTO", newPp);

        return "Addportpolio";
    }

    //새 포트폴리오 등록
    @PostMapping(value="/addportpolioRequest")
    public String CreateNewPortpolio(@Valid @ModelAttribute("PortpolioFormDTO")PortpolioFormDTO request, BindingResult bindingResult,
                                     @RequestParam("portpolioimg")MultipartFile portpolioimg) {

        if (bindingResult.hasErrors()) {

            return "Addportpolio";
        }

        if (!portpolioimg.isEmpty()) {
            String orgFilename = portpolioimg.getOriginalFilename();                                         // 원본 파일명
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");           // 32자리 랜덤 문자열
            String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1);  // 확장자
            String saveFilename = uuid + "." + extension;                                             // 디스크에 저장할 파일명
            String fileFullPath = Paths.get(uploadPortpolioDir, saveFilename).toString();                      // 디스크에 저장할 파일의 전체 경로
            System.out.println(fileFullPath+"이거");

            // uploadDir에 해당되는 디렉터리가 없으면, uploadDir에 포함되는 전체 디렉터리 생성
            File dir = new File(uploadPortpolioDir);
            if (dir.exists() == false) {
                dir.mkdirs();
            }

            try {
                // 파일 저장 (write to disk)
                File uploadFile = new File(fileFullPath);
                portpolioimg.transferTo(uploadFile);
                request.setImgUrl("/image-print/portpolio?filename="+saveFilename);

            } catch (IOException e) {
                // 예외 처리는 따로 해주는 게 좋습니다.
                throw new RuntimeException(e);
            }

        }

        portpolioService.addPortpolio(request);



        return "redirect:/portpolioList";
    }


    @GetMapping(value = "/portpolio")
    public String goPortpolioPage(Model model,@RequestParam Long id) {

        PortpolioViewDTO getPortPolio= portpolioService.getPortpolio(id);


        model.addAttribute("PortpolioViewDTO",  getPortPolio);

        return "portpolio";
    }

    //포트폴리오 수정
    @PostMapping(value = "/modifyportpolio")
    public String ModifyPortpolio(Model model,@RequestParam Long id) {

        PortpolioViewDTO getPortPolio= portpolioService.getPortpolio(id);


        model.addAttribute("PortpolioDTO",  getPortPolio);

        return "Modifyportpolio";
    }

    //포트폴리오 수정 동작
    @Transactional
    @PostMapping(value="/modifyportpolioRequest")
    public String CreateNewUser(@Valid @ModelAttribute("PortpolioDTO")PortpolioViewDTO request, BindingResult bindingResult,
                                @RequestParam("profileimg")MultipartFile profileimg) {

        System.out.println(profileimg.getOriginalFilename());
        if (bindingResult.hasErrors()) {
            return "Modifyportpolio";
        }

        if (!profileimg.isEmpty()) {
            String orgFilename = profileimg.getOriginalFilename();                                         // 원본 파일명
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");           // 32자리 랜덤 문자열
            String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1);  // 확장자
            String saveFilename = uuid + "." + extension;                                             // 디스크에 저장할 파일명
            String fileFullPath = Paths.get(uploadPortpolioDir, saveFilename).toString();                      // 디스크에 저장할 파일의 전체 경로
            System.out.println(fileFullPath+"이거");

            // uploadDir에 해당되는 디렉터리가 없으면, uploadDir에 포함되는 전체 디렉터리 생성
            File dir = new File(uploadPortpolioDir);
            if (dir.exists() == false) {
                dir.mkdirs();
            }

            try {
                // 파일 저장 (write to disk)
                File uploadFile = new File(fileFullPath);
                profileimg.transferTo(uploadFile);
                request.setImgUrl("/image-print/portpolio?filename="+saveFilename);

            } catch (IOException e) {
                // 예외 처리는 따로 해주는 게 좋습니다.
                throw new RuntimeException(e);
            }

        }

        portpolioService.updatePortpolio(request);

        return "redirect:portpolioList";
    }

    @PostMapping(value = "/deleteportpolio")
    public String DeletePortpolio(@RequestParam Long id) {

           portpolioService.deletePortpolio(id);

           return "portpolioList";
    }


    //포트폴리오 수정
    @PostMapping(value = "/modifyguestbookView")
    public String ModifyPortpolioView(Model model,@RequestParam Long id) {

        guestBookViewDTO getGuestBook= service.getPortpolio(id);

        model.addAttribute("guestBook",  getGuestBook);

        return "Modifycontent";
    }





    }

