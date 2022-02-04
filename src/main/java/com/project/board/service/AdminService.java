package com.project.board.service;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.entity.UserEntity;
import com.project.board.entity.WriteEntity;
import com.project.board.handler.ex.CustomStandardValidationException;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.repository.UserRepository;
import com.project.board.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    @Transactional
    public Page<UserEntity> 회원목록(int pageNum, String option, String keyword, UserEntity userEntity) {

        if(userEntity.getRole() != Role.ADMIN) {
            throw  new CustomStandardValidationException("관리자가 아니므로 접근할 수 없습니다.");
        }

        Pageable pageable = PageRequest.of(pageNum, 5, Sort.by("createDate").descending());

        if(keyword == null || keyword.isEmpty()) {
            Page<UserEntity> writeList = userRepository.findAll(pageable);

            return writeList;

        }else {
            switch (option) {
                case "username":
                    Page<UserEntity> byTitleContaining = userRepository.findByUsernameContainingIgnoreCase(keyword, pageable);
                    return byTitleContaining;
                case "name":
                    Page<UserEntity> byContentContaining = userRepository.findByNameContainingIgnoreCase(keyword, pageable);
                    return byContentContaining;
                default:
                    Page<UserEntity> all = userRepository.findAll(pageable);
                    return all;
            }
        }

    }

    @Transactional
    public UserEntity 회원정보(Long userId, UserEntity userEntity) {

        if(userEntity.getRole() != Role.ADMIN) {
            throw  new CustomStandardValidationException("관리자가 아니므로 접근할 수 없습니다.");
        }

        UserEntity findUser = userRepository.findById(userId).get();
//        UserEntity findUser = userRepository.findById(userId).orElseThrow(() -> {
//            throw new CustomValidationException("회원 정보가 없습니다.");
//        });

        return findUser;

    }

    @Transactional
    public void 회원삭제(Long userId, UserEntity userEntity) {


        if(userEntity.getId() == userId) {
            throw new CustomStandardValidationException("로그인 한 계정은 삭제 할 수 없습니다.");
        }

//        UserEntity findUser = userRepository.findById(userId).orElseThrow(() -> {
//            throw new CustomValidationException("회원 정보가 없습니다.");
//        });

        UserEntity findUser = userRepository.findById(userId).get();

        userRepository.deleteById(userId);
    }
}
