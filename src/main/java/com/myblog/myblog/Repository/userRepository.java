package com.myblog.myblog.Repository;

import com.myblog.myblog.Entity.User;
import com.myblog.myblog.Entity.guestBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<User, String> {

    User findByEmail(String email);

    Page<User> findByUsernameContaining(String username, Pageable pageable);


}
