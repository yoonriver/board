package com.project.board.service;

import com.project.board.entity.UserEntity;
import com.project.board.repository.AuthRepository;
import com.project.board.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void 회원가입(UserEntity userEntity) {

        String rawPassword = userEntity.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        userEntity.setPassword(encodedPassword);
        userEntity.setRole(Role.USER);

        authRepository.save(userEntity);

    }

    @Transactional
    public boolean 아이디확인(String username) {

        boolean isExists = authRepository.existsByUsername(username);

        return isExists;
    }

    @Transactional
    public boolean 이메일확인(String userEmail) {

        boolean isExists = authRepository.existsByUserEmail(userEmail);

        return isExists;
    }
}
