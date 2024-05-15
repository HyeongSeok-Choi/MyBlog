package com.myblog.myblog.Dto;

import com.myblog.myblog.Entity.Board;
import com.myblog.myblog.Entity.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortpolioListResponse {

    private Long id;

    private String title;

    private String name;

    private String email;

    private String phoneNumber;

    private String OneLineComment;

    private String mySkill;

    private String Introduction;

    private String imgUrl;

    public LocalDateTime createdAt;

    public LocalDateTime updatedAt;

    public PortpolioListResponse(Portfolio portfolio) {
        this.id = portfolio.getId();
        this.title = portfolio.getTitle();
        this.name = portfolio.getName();
        this.email = portfolio.getEmail();
        this.phoneNumber = portfolio.getPhoneNumber();
        this.OneLineComment = portfolio.getOneLineComment();
        this.mySkill = portfolio.getMySkill();
        this.Introduction = portfolio.getIntroduction();
        this.imgUrl = portfolio.getImgUrl();
        this.createdAt = portfolio.getCreatedAt();
        this.updatedAt = portfolio.getUpdatedAt();
    }

}
