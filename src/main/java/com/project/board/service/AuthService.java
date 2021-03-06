package com.project.board.service;

import com.project.board.entity.UserEntity;
import com.project.board.handler.ex.CustomStandardValidationException;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.repository.UserRepository;
import com.project.board.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${spring.mail.username}")
    private String FROM_MAIL;
    private final JavaMailSender mailSender;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void 회원가입(UserEntity userEntity) {

        if (userRepository.existsByUsername(userEntity.getUsername())) {
            throw new CustomStandardValidationException("이미 존재하는 id 입니다.");
        }

        if (userRepository.existsByUserEmail(userEntity.getUserEmail())) {
            throw new CustomStandardValidationException("이미 존재하는 이메일 입니다.");
        }

        String rawPassword = userEntity.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        userEntity.setPassword(encodedPassword);
        userEntity.setRole(Role.USER);

        userRepository.save(userEntity);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEntity.getUsername(), rawPassword));
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Transactional
    public boolean 아이디확인(String username) {

        boolean isExists = userRepository.existsByUsername(username);

        return isExists;
    }

    @Transactional
    public boolean 이메일확인(String userEmail) {

        boolean isExists = userRepository.existsByUserEmail(userEmail);

        return isExists;
    }

    public void 메일보내기(UserEntity userEntity, String tempPw, String option){

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(userEntity.getUserEmail());
            message.setFrom(FROM_MAIL);
            if(option.equals("id")) {
                message.setSubject(userEntity.getName() + "님의 아이디 입니다.");
                message.setText(userEntity.getName() + "님의 아이디는 " + userEntity.getUsername() + " 입니다.");
            }else if(option.equals("pw")) {
                message.setSubject(userEntity.getName() + "님의 임시 비밀번호 입니다.");
                message.setText(userEntity.getName() + "님의 비밀번호는 " + tempPw + " 입니다. 비밀번호를 꼭 변경해주세요.");
            }
            mailSender.send(message);

        } catch (Exception e) {
            throw new CustomStandardValidationException(e.getMessage());
        }
    }

    public void 아이디찾기(String userEmail) {


        UserEntity userEntity = userRepository.findByUserEmail(userEmail).orElseThrow(() -> {
            throw new CustomStandardValidationException("일치하는 이메일이 없습니다.");
        });

        메일보내기(userEntity, null, "id");
    }

    @Transactional
    public void 비밀번호찾기(String username, String userEmail) {

        userRepository.findByUsername(username).orElseThrow(() -> {
            throw new CustomStandardValidationException("일치하는 아이디가 없습니다.");
        });

        UserEntity userEntity = userRepository.findByUserEmail(userEmail).orElseThrow(() -> {
            throw new CustomStandardValidationException("일치하는 이메일이 없습니다.");
        });

        String tempPw = UUID.randomUUID().toString().substring(0, 8);
        String encodedPassword = bCryptPasswordEncoder.encode(tempPw);
        userEntity.setPassword(encodedPassword);

        메일보내기(userEntity, tempPw, "pw");
    }

}
