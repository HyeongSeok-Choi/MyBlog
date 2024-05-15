package com.myblog.myblog.Service;


import com.myblog.myblog.Dto.*;
import com.myblog.myblog.Entity.Board;
import com.myblog.myblog.Entity.Portfolio;
import com.myblog.myblog.Entity.guestBook;
import com.myblog.myblog.Repository.boardRepository;
import com.myblog.myblog.Repository.guestBookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class guestBookService {

    private final guestBookRepository repository;


    //전체 방명록 반환
    public List<guestBook> findAll(){
        return repository.findAll();
    }

    //페이징된 방명록 반환
    public Page<guestBook> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }


    //제목으로 검색하기
    public Page<guestBook> findAllByTitle(String title, Pageable pageable){

        return repository.findByTitleContaining(title, pageable);
    }

    //방명록 저장
    public guestBook save(AddguestBookRequest request){
        return repository.save(request.toEntity());
    }

    //id에 해당하는 포트폴리오 반환(상세보기에 사용)

    public guestBookViewDTO getPortpolio(Long id) {

        guestBookViewDTO guestBookViewdto = new guestBookViewDTO();


        //optional때문에 get으로 다시 반환
        guestBookViewdto.getGuestBookViewDTO(repository.findById(id).get());

        return guestBookViewdto;

    }

    @Transactional
    public guestBook update(long id, guestBookViewDTO request){
        guestBook guestBook = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : "+id));
        System.out.println(request.getId());
        System.out.println(request.getUserid());
        System.out.println(request.getContent());
        System.out.println(request.getId()+"여기 오다1!!");

        guestBook.update(request.getContent(), request.getAuthor(),request.getTitle());


        return guestBook;
    }

    public void delete(long id){
        repository.deleteById(id);
    }


}
