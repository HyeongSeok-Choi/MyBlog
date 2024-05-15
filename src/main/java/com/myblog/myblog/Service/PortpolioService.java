package com.myblog.myblog.Service;


import com.myblog.myblog.Dto.PortpolioFormDTO;
import com.myblog.myblog.Dto.PortpolioViewDTO;
import com.myblog.myblog.Dto.UserFormDTO;
import com.myblog.myblog.Entity.Board;
import com.myblog.myblog.Entity.Portfolio;
import com.myblog.myblog.Entity.User;
import com.myblog.myblog.Repository.portfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortpolioService {

    private final portfolioRepository repository;




    public PortpolioViewDTO getMainPortpolio(){


        Portfolio portfolio = repository.findByMainPortfolio(true);

        PortpolioViewDTO portpolioViewDTO = new PortpolioViewDTO();

        if(portfolio != null) {

            portpolioViewDTO.setId(portfolio.getId());
            portpolioViewDTO.setTitle(portfolio.getTitle());
            portpolioViewDTO.setName(portfolio.getName());
            portpolioViewDTO.setCreatedAt(portfolio.getCreatedAt());
            portpolioViewDTO.setUpdatedAt(portfolio.getUpdatedAt());
            portpolioViewDTO.setImgUrl(portfolio.getImgUrl());
            portpolioViewDTO.setEmail(portpolioViewDTO.getEmail());
            portpolioViewDTO.setPhoneNumber(portpolioViewDTO.getPhoneNumber());
            portpolioViewDTO.setOneLineComment(portpolioViewDTO.getOneLineComment());

        }else{
            portpolioViewDTO=null;
        }


        return portpolioViewDTO;

    }


    @Transactional
    public void ChangeMainportfolio(Long portpolioid){

        //기존 대표 포트폴리오


        Portfolio findedExistMainPp = repository.findByMainPortfolio(true);

        if(findedExistMainPp != null) {

            findedExistMainPp.setMainPortfolio(false);

            repository.save(findedExistMainPp);
        }

        //대표 포트폴리오 설정
        Portfolio findedPortfilid=  repository.findById(portpolioid).get();

        findedPortfilid.setMainPortfolio(true);


        repository.save(findedPortfilid);


    }



    //id에 해당하는 포트폴리오 반환(상세보기에 사용)
    public PortpolioViewDTO getPortpolio(Long id) {

        PortpolioViewDTO getPortpolioViewDTO = new PortpolioViewDTO();
        //optional때문에 get으로 다시 반환
        getPortpolioViewDTO.getPortpolioViewDTO(repository.findById(id).get());

        return getPortpolioViewDTO;

    }


    //포트폴리오 추가
    @Transactional
    public Portfolio addPortpolio(PortpolioFormDTO portpolioFormDTO) {

        Portfolio portfolio = Portfolio.builder()
                .title(portpolioFormDTO.getTitle())
                .name(portpolioFormDTO.getName())
                .email(portpolioFormDTO.getEmail())
                .phoneNumber(portpolioFormDTO.getPhoneNumber())
                .OneLineComment(portpolioFormDTO.getOneLineComment())
                .mySkill(portpolioFormDTO.getMySkill())
                .Introduction(portpolioFormDTO.getIntroduction())
                .imgUrl(portpolioFormDTO.getImgUrl())
                .mainPortfolio(false)
                .build();


        return repository.save(portfolio);

    }

    //전체 포트폴리오 반환
    public List<Portfolio> findAll(){
        return repository.findAll();
    }


    //페이징된 포트폴리오 반환
    public Page<Portfolio> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }


    //제목으로 검색하기
    public Page<Portfolio> findAllByTitle(String title, Pageable pageable){

        return repository.findByTitleContaining(title, pageable);
    }


    //포트폴리오 수정
    @Transactional
    public Portfolio updatePortpolio(PortpolioViewDTO portpolioViewDTO) {

        Portfolio portfolio = portpolioViewDTO.toEntity();


        return repository.save(portfolio);

    }

    //포트폴리오 삭제
    @Transactional
    public void deletePortpolio(Long id) {
        repository.deleteById(id);
    }





}
