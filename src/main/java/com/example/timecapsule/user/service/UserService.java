package com.example.timecapsule.user.service;

import com.example.timecapsule.exception.NotFoundException;
import com.example.timecapsule.user.dto.TokenResponseDto;
import com.example.timecapsule.user.dto.UserRequestDto;
import com.example.timecapsule.user.entity.Auth;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.jwt.JwtTokenProvider;
import com.example.timecapsule.user.repository.AuthRepository;
import com.example.timecapsule.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(UserRequestDto userRequestDto){
        String encodedPw = passwordEncoder.encode(userRequestDto.getUserPw());

        User user = User.builder()
                .userId(userRequestDto.getUserId())
                .userPw(encodedPw)
                .userNickname(userRequestDto.getUserNickname())
                .userEmail(userRequestDto.getUserEmail())
                .build();
        userRepository.save(user);
        return user;
    }

    public boolean isUserIdDuplicated (String userId){
        Optional<User> user = userRepository.findUserByUserId(userId);
        return user.isPresent();
    }

    public boolean isUserNicknameDuplicated (String userNickname){
        Optional<User> user = userRepository.findUserByUserNickname(userNickname);
        return user.isPresent();
    }

    public TokenResponseDto login(UserRequestDto userRequestDto) throws Exception{
        User user = userRepository.findUserByUserId(userRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User does not exist."));

        if (!passwordEncoder.matches(userRequestDto.getUserPw(), user.getUserPw())){
            throw new Exception("Wrong password.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUserId());

        Auth auth = Auth.builder()
                .userId(user.getUserId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        authRepository.save(auth);

        return TokenResponseDto.builder()
                .ACCESS_TOKEN(accessToken)
                .REFRESH_TOKEN(refreshToken)
                .build();
    }

    public User findUserByAccessToken(String accessToken) {
//        jwtTokenProvider.getUserInfoFromToken(accessToken);
//        Auth auth=authRepository.findAuthByAccessToken(accessToken);
        log.info("유저의 정보는 : {}",jwtTokenProvider.getUserInfoFromToken(accessToken));
        return userRepository.findUserByUserId(jwtTokenProvider.getUserInfoFromToken(accessToken)).orElseThrow(NotFoundException::new);
    }

}
