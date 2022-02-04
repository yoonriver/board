package com.project.board.service;

import com.project.board.entity.UserEntity;
import com.project.board.handler.ex.CustomValidationApiException;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.repository.UpdateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateService {

    private final UpdateRepository updateRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserEntity 회원수정(Long id, UserEntity userDto) {
//        UserEntity userEntity  = updateRepository.findById(id).orElseThrow(() -> {return new CustomValidationApiException("찾을 수 없는 id입니다.");});
        UserEntity userEntity  = updateRepository.findById(id).get();

        // Validate 체크
        if(userEntity.getOauth() == null || userEntity.getOauth().equals("")) {
            userEntity.setUserEmail(userDto.getUserEmail());
            userEntity.setName(userDto.getName());
        }

        return userEntity;

    }

    @Transactional
    public UserEntity 비밀번호수정(Long id, UserEntity userDto, String modPwd) {
//        UserEntity userEntity  = updateRepository.findById(id).orElseThrow(() -> {return new CustomValidationException("찾을 수 없는 id입니다.");});
        UserEntity userEntity  = updateRepository.findById(id).get();

        // Validate 체크
        if(userEntity.getOauth() == null || userEntity.getOauth().equals("")) {
            String rawPwd = modPwd;
            String encPwd = bCryptPasswordEncoder.encode(rawPwd);

            userEntity.setPassword(encPwd);
        }

        return userEntity;

    }
}
