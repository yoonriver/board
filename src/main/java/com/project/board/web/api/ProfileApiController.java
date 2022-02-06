package com.project.board.web.api;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.dto.CMRespDto;
import com.project.board.dto.UserPwUpdateDto;
import com.project.board.dto.UserUpdateDto;
import com.project.board.entity.UserEntity;
import com.project.board.handler.ex.CustomPwUpdateValidationException;
import com.project.board.handler.ex.CustomUpdateValidationException;
import com.project.board.service.UpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ProfileApiController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UpdateService updateService;

    @PutMapping("api/profile/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                               @Valid UserUpdateDto userUpdateDto,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(bCryptPasswordEncoder.matches(userUpdateDto.getPassword(), principalDetails.getPassword())) {
            UserEntity userEntity = updateService.회원수정(id, userUpdateDto.toEntity());

            principalDetails.setUserEntity(userEntity);

            return new ResponseEntity<>(new CMRespDto<>(1, "회원 수정 완료", userEntity), HttpStatus.OK);

        }else {
            throw new CustomUpdateValidationException("현재 비밀번호가 아닙니다.");
        }

    }

    @PutMapping("api/profile/pw-update/{id}")
    public ResponseEntity<?> pwUpdate(@PathVariable Long id,
                               @Valid UserPwUpdateDto userPwUpdateDto,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(bCryptPasswordEncoder.matches(userPwUpdateDto.getPassword(), principalDetails.getPassword())) {
            if(userPwUpdateDto.getMod_password1().equals(userPwUpdateDto.getMod_password2())) {

                UserEntity userEntity = updateService.비밀번호수정(id, userPwUpdateDto.toEntity(), userPwUpdateDto.getMod_password1());

                principalDetails.setUserEntity(userEntity);

                return new ResponseEntity<>(new CMRespDto<>(1, "비밀번호 수정 완료", userEntity), HttpStatus.OK);

            }else {
                throw new CustomPwUpdateValidationException("확인 비밀번호가 일치하지 않습니다.");
            }

        }else {
            throw new CustomUpdateValidationException("현재 비밀번호가 아닙니다.");
        }

    }
}
