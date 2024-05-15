package com.myblog.myblog.Repository;


import com.myblog.myblog.Entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    //List<Reply> findByBoardId(Long id);
}
