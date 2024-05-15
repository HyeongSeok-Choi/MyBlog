package com.myblog.myblog.Service;

import com.myblog.myblog.Dto.UserFormDTO;
import com.myblog.myblog.Entity.Portfolio;
import com.myblog.myblog.Entity.User;
import com.myblog.myblog.Enum.Role;
import com.myblog.myblog.Repository.userRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final userRepository userRepository;

    @Transactional
    public void updateRole(String email){

        User user = userRepository.findByEmail(email);
        user.setRole(Role.pUSER);

        userRepository.save(user);

    }

    //페이징된 멤버리스트 반환
    public Page<User> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }


    //제목으로 검색하기
    public Page<User> findAllByUsername(String username, Pageable pageable){

        return userRepository.findByUsernameContaining(username, pageable);
    }


    //회원 저장
    public User save(User user) {

        User JoinUser = userRepository.save(user);

        return JoinUser;
    }

    //중복 아이디 여부 확인
    public boolean CheckEmail(String email) {
        return userRepository.existsById(email);
    }


    //이메일로 회원 찾기
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public void  validateDuplicateMember(User user){
        User findUser = userRepository.findByEmail(user.getEmail());
        if(findUser != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException(email);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }



}
