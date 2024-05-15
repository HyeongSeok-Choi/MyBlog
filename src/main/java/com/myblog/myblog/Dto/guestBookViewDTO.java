package com.myblog.myblog.Dto;


import com.myblog.myblog.Entity.Portfolio;
import com.myblog.myblog.Entity.guestBook;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class guestBookViewDTO {

    public  Long id;
    public  String title;
    public  String content;
    public  LocalDateTime createdAt;
    public  LocalDateTime updatedAt;
    public  String userid;
    public  String author;




    //entity -> DTO
    public guestBookViewDTO getGuestBookViewDTO (guestBook guestBook) {

        this.author = guestBook.getAuthor();
        this.id = guestBook.getId();
        this.title = guestBook.getTitle();
        this.content = guestBook.getContent();
        this.createdAt = guestBook.getCreatedAt();
        this.updatedAt = guestBook.getUpdatedAt();
        this.userid = guestBook.getUserId();


        return this;
    }

    //DTO -> entity
    public guestBook toEntity () {

        guestBook guestBook = new guestBook();
        guestBook.setId(id);
        guestBook.setTitle(title);
        guestBook.setContent(content);
        guestBook.setCreatedAt(createdAt);
        guestBook.setUpdatedAt(updatedAt);
        guestBook.setAuthor(author);
        guestBook.setUserId(userid);

        return guestBook;
    }

}
