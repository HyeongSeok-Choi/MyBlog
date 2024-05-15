package com.myblog.myblog.Repository;

import com.myblog.myblog.Entity.Portfolio;
import com.myblog.myblog.Entity.guestBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface guestBookRepository extends JpaRepository<guestBook,Long> {


    Page<guestBook> findByTitleContaining(String title, Pageable pageable);

    guestBook findByUserId(String username);

}
