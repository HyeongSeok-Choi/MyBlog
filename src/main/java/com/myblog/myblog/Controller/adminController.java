package com.myblog.myblog.Controller;

import com.myblog.myblog.Entity.Portfolio;
import com.myblog.myblog.Entity.User;
import com.myblog.myblog.Service.PortpolioService;
import com.myblog.myblog.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class adminController {

    private final UserService userService;
    private final PortpolioService portpolioService;


    //회원 목록 리스트
    @GetMapping(value = "/page")
    public String UserFormDTO(Model model, @PageableDefault(page=0,size = 5) Pageable pageable, String username) {

        Page<User> users ;

        if(username == null){
            users = userService.findAll(pageable);

        }else{
            users = userService.findAllByUsername(username,pageable);
        }


        int nowPage = users.getPageable().getPageNumber()+1;
        int startPage=Math.max(nowPage-4,1);
        int endPage=Math.min(nowPage+5,users.getTotalPages());



        if(!users.isEmpty()) {
            model.addAttribute("Users", users);
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            return "admin";
        }

        model.addAttribute("nowPage", null);
        model.addAttribute("startPage", null);
        model.addAttribute("endPage", null);

        return "admin";
    }


    @PostMapping(value = "/updateRole")
    public String updateRole(@RequestParam String userId) {

        userService.updateRole(userId);

        return "redirect:/admin/page";
    }


    //대표 포트폴리오 저장 위한 리스트
    @GetMapping(value = {"/changePp"})
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
            return "adminChangePp";
        }

        model.addAttribute("nowPage", null);
        model.addAttribute("startPage", null);
        model.addAttribute("endPage", null);

        return "adminChangePp";
    }



    @PostMapping(value = "/updateMainPp")
    public String updateMainPp(@RequestParam Long portfolioId) {


        portpolioService.ChangeMainportfolio(portfolioId);

        return "redirect:/admin/changePp";
    }



}
