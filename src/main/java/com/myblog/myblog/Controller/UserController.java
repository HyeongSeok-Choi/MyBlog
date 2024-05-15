package com.myblog.myblog.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myblog.myblog.Dto.KakaoProfile;
import com.myblog.myblog.Dto.OAuthToken;
import com.myblog.myblog.Dto.UserFormDTO;
import com.myblog.myblog.Entity.User;
import com.myblog.myblog.Service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    //회원가입 프로필 이미지 경로
    private final String uploadProfileDir = Paths.get("C:", "MemberProfileImg", "upload").toString();
    private final PasswordEncoder passwordEncoder ;


    @GetMapping(value="/join")
    public String memberForm(Model model){
        model.addAttribute("UserFormDTO", new UserFormDTO());
        return "Join";

    }


    //가입등록
    @Transactional
    @PostMapping(value="/join")
    public String CreateNewUser(@Valid @ModelAttribute("UserFormDTO")UserFormDTO request, BindingResult bindingResult,
                                @RequestParam("profileimg") MultipartFile profileimg, Model model) {

        System.out.println(profileimg.getOriginalFilename());
        if (bindingResult.hasErrors()) {
            return "join";
        }



        if (!profileimg.isEmpty()) {
            String orgFilename = profileimg.getOriginalFilename();                                         // 원본 파일명
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");           // 32자리 랜덤 문자열
            String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1);  // 확장자
            String saveFilename = uuid + "." + extension;                                             // 디스크에 저장할 파일명
            String fileFullPath = Paths.get(uploadProfileDir, saveFilename).toString();                      // 디스크에 저장할 파일의 전체 경로
            System.out.println(fileFullPath+"이거");

            // uploadDir에 해당되는 디렉터리가 없으면, uploadDir에 포함되는 전체 디렉터리 생성
            File dir = new File(uploadProfileDir);
            if (dir.exists() == false) {
                dir.mkdirs();
            }

            try {
                // 파일 저장 (write to disk)
                File uploadFile = new File(fileFullPath);
                profileimg.transferTo(uploadFile);
                request.setProfileImgUrl("/image-print/user?filename="+saveFilename);

            } catch (IOException e) {
                // 예외 처리는 따로 해주는 게 좋습니다.
                throw new RuntimeException(e);
            }

        }

        try {

            User user = User.createUser(request,passwordEncoder);

            userService.save(user);

        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "join";
        }

        return "main";
    }

    @GetMapping(value = "/login")
    public String loginMember(){
        return "login";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "이메일 또는 비밀번호를 확인해주세요");
        return "login";
    }


    @GetMapping(value = "/auth/kakao/callback")
    public String kakaoCallback(@RequestParam String code , Model model){
/*
        //code로 accessToken받기
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "9739812df088ab6e1028c14756aa0072");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        OAuthToken oAuthToken =  new GsonBuilder().create().fromJson(response.getBody().toString(), OAuthToken.class);


        //accessToken으로 정보 받아오기

        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization","Bearer "+oAuthToken.getAccess_token());
        headers2.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");


        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        KakaoProfile kakaoProfile = new GsonBuilder().create().fromJson(response2.getBody().toString(),KakaoProfile.class);

        System.out.println(kakaoProfile.getKakao_account().email);



        model.addAttribute("accessToken", oAuthToken.getAccess_token());
        model.addAttribute("kakaoCallback", code);
        model.addAttribute("kakaoToken", response2.getBody());



*/

        return "kakaoCallback";
    }



}
