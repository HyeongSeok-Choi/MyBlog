package com.myblog.myblog.Dto;

import com.myblog.myblog.Entity.Board;
import com.myblog.myblog.Entity.guestBook;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddguestBookRequest {

    private String title;
    private String content;
    private String userid;
    private String author;


    public guestBook toEntity(){
        guestBook guestBook = new guestBook();

        guestBook.setTitle(title);
        guestBook.setContent(content);
        guestBook.setAuthor(author);
        guestBook.setUserId(userid);

        return guestBook;

    }
}
