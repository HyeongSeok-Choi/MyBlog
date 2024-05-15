package com.myblog.myblog.Dto;

import com.myblog.myblog.Entity.Board;
import com.myblog.myblog.Entity.guestBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class guestBookListResponse {

    public  Long id;
    public  String title;
    public  String content;
    public  LocalDateTime createdAt;
    public  String userid;
    public  String author;

    public guestBookListResponse(guestBook guestBook) {
        this.id = guestBook.getId();
        this.author = guestBook.getAuthor();
        this.content = guestBook.getContent();
        this.createdAt = guestBook.getCreatedAt();
        this.userid = guestBook.getUserId();
        this.title = guestBook.getTitle();
    }
}
