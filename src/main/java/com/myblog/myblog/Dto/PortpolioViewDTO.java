package com.myblog.myblog.Dto;


import com.myblog.myblog.Entity.Portfolio;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortpolioViewDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    private String name;

    private String email;

    private String phoneNumber;

    @NotBlank(message = "코멘트 한 줄을 입력해주세요 !")
    @Size(min = 1, max = 255,message = "내용을 255자 이하로 적어주세요")
    private String OneLineComment;

    @NotBlank(message = "스킬은 필수 사항입니다")
    private String mySkill;

    private String Introduction;

    private String imgUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;



    public PortpolioViewDTO getPortpolioViewDTO (Portfolio portfolio) {

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

        return this;
    }

    public Portfolio toEntity () {

        Portfolio portfolio = new Portfolio();
        portfolio.setId(id);
        portfolio.setTitle(title);
        portfolio.setName(name);
        portfolio.setEmail(email);
        portfolio.setPhoneNumber(phoneNumber);
        portfolio.setOneLineComment(OneLineComment);
        portfolio.setMySkill(mySkill);
        portfolio.setIntroduction(Introduction);
        portfolio.setImgUrl(imgUrl);
        portfolio.setCreatedAt(createdAt);
        portfolio.setUpdatedAt(updatedAt);
        return portfolio;
    }

}
