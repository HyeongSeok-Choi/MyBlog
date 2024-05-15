package com.myblog.myblog.Controller;


import com.myblog.myblog.Dto.*;
import com.myblog.myblog.Entity.Board;
import com.myblog.myblog.Entity.User;
import com.myblog.myblog.Entity.guestBook;
import com.myblog.myblog.Service.PortpolioService;
import com.myblog.myblog.Service.UserService;
import com.myblog.myblog.Service.guestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class apiController {


    private final guestBookService GuestBookservice;
    private final UserService userService;
    private final PortpolioService portpolioService;




    //방명록 새글 등록 api
    @PostMapping("/api/newContent")
    public ResponseEntity<guestBook> addGuestBook(@RequestBody AddguestBookRequest request, Principal principal) {

        if(principal != null) {
            User user = userService.findByEmail(principal.getName());

            request.setAuthor(user.getRealUserName());
            request.setUserid(user.getUsername());
        }
        guestBook savedContent = GuestBookservice.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedContent);
    }


    //방명록 수정 api
    @PostMapping("/api/modify/guestBook")
    public ResponseEntity<guestBookViewDTO> modifyguestBook(@RequestBody guestBookViewDTO request) {

        long id = request.getId();

        GuestBookservice.update(id,request);

        return ResponseEntity.ok().body(request);

    }


    //방명록 삭제 api
    @DeleteMapping("/api/delete/guestbook/{id}")
    public ResponseEntity<Void> deleteGuestBook(@PathVariable Long id){
        GuestBookservice.delete(id);
        return ResponseEntity.ok()
                .build();
    }





}