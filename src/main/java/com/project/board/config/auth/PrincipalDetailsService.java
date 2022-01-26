package com.project.board.config.auth;

import com.project.board.entity.UserEntity;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = authRepository.findByUsername(username).get();

        if(userEntity == null) {
            return null;

        }else {
            return new PrincipalDetails(userEntity);
        }
    }
}
