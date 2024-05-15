package com.myblog.myblog.Dto;


import com.myblog.myblog.Entity.Board;
import com.myblog.myblog.Entity.Portfolio;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortpolioFormDTO {

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    private String name;

    private String email;

    private String phoneNumber;


    @Size(max = 50,message = "한 줄 치곤 길다고 생각합니다.")
    private String OneLineComment;

    @NotBlank(message = "스킬을 적어주세요")
    private String mySkill;

    @NotBlank(message = "이 부분이 제일 중요한 정보입니다 !!!")
    @Size(min = 50,message = "50자 이상은 적어주세요")
    private String Introduction;

    private String imgUrl;


    public PortpolioFormDTO getPortpolioFormDTO (Portfolio portfolio) {

        this.setTitle(portfolio.getTitle());
        this.setName(portfolio.getName());
        this.setEmail(portfolio.getEmail());
        this.setPhoneNumber(portfolio.getPhoneNumber());
        this.setOneLineComment(portfolio.getOneLineComment());
        this.setMySkill(portfolio.getMySkill());
        this.setIntroduction(portfolio.getIntroduction());
        this.setImgUrl(portfolio.getImgUrl());
        return this;
    }

    public Portfolio toEntity(){
        Portfolio portfolio = new Portfolio();

        portfolio.setTitle(this.title);
        portfolio.setName(this.name);
        portfolio.setEmail(this.email);
        portfolio.setPhoneNumber(this.phoneNumber);
        portfolio.setOneLineComment(this.OneLineComment);
        portfolio.setMySkill(this.mySkill);
        portfolio.setIntroduction(this.Introduction);
        portfolio.setImgUrl(this.imgUrl);


        return portfolio;

    }

}
