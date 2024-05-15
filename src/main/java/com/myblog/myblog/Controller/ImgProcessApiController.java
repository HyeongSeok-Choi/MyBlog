package com.myblog.myblog.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//toast-ui editor image 처리
@RestController
@RequestMapping(value = "/image-print")
public class ImgProcessApiController {

    //회원가입 프로필 이미지 경로
    private final String uploadProfileDir = Paths.get("C:", "MemberProfileImg", "upload").toString();
    //포트폴리오 이미지 경로
    private final String uploadPortpolioDir = Paths.get("C:", "PortpolioImg", "upload").toString();
    //toast-ui(방명록 이미지 등록)



        //포트폴리오 이미지 처리
        @GetMapping(value = "/portpolio", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
        public byte[] printPortpolioImage(@RequestParam final String filename) {

            System.out.println(filename+"파일 요있네");
            // 업로드된 파일의 전체 경로
            String fileFullPath = Paths.get(uploadPortpolioDir, filename).toString();

            // 파일이 없는 경우 예외 throw
            File uploadedFile = new File(fileFullPath);
            if (uploadedFile.exists() == false) {
                throw new RuntimeException();
            }

            try {
                // 이미지 파일을 byte[]로 변환 후 반환
                byte[] imageBytes = Files.readAllBytes(uploadedFile.toPath());
                return imageBytes;

            } catch (IOException e) {

                throw new RuntimeException(e);
            }
        }


    //회원 프로필 이미지 처리
    @GetMapping(value = "/user", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] printUserImage(@RequestParam final String filename) {

        System.out.println(filename+"파일 요있네");
        // 업로드된 파일의 전체 경로
        String fileFullPath = Paths.get(uploadProfileDir, filename).toString();

        // 파일이 없는 경우 예외 throw
        File uploadedFile = new File(fileFullPath);
        if (uploadedFile.exists() == false) {
            throw new RuntimeException();
        }

        try {
            // 이미지 파일을 byte[]로 변환 후 반환
            byte[] imageBytes = Files.readAllBytes(uploadedFile.toPath());
            return imageBytes;

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    }

