package com.myblog.myblog.Controller;

import com.myblog.myblog.Dto.AddReply;
import com.myblog.myblog.Dto.AddguestBookRequest;
import com.myblog.myblog.Entity.Reply;
import com.myblog.myblog.Entity.User;
import com.myblog.myblog.Entity.guestBook;
import com.myblog.myblog.Service.replyService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class apiReplyController {


    private final replyService replyservice;

    //방명록 새글 등록 api
    @PostMapping("/newReply")
    public ResponseEntity<Reply> addReply(@RequestBody AddReply request, Principal principal) {
        System.out.println(request.getAuthor());
        System.out.println(request.getContent());
        System.out.println(request.getBoardId());

        request.setAuthor(principal.getName());

        Reply Savedreply  = replyservice.addReply(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Savedreply);
    }

    /*
    @GetMapping(value = "/viewReply/{id}")
    public ResponseEntity<List> viewReply(Principal principal, @RequestParam Long id ) {

        List<Reply> replyList = replyservice.getReplies(id);

        return ResponseEntity.ok(replyList);
    }

     */
}
