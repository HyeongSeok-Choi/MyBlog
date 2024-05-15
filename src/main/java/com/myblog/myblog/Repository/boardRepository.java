package com.myblog.myblog.Repository;

import com.myblog.myblog.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface boardRepository extends JpaRepository<Board, Long> {
}
