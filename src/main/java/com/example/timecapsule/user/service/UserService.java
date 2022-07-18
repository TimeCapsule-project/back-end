package com.example.timecapsule.user.service;


import com.example.timecapsule.user.dto.response.KakaoResponse;
import com.example.timecapsule.exception.*;
import com.example.timecapsule.user.dto.response.TokenResponseDto;
import com.example.timecapsule.user.dto.request.UserRequestDto;
import com.example.timecapsule.user.dto.response.UserResponseDto;
import com.example.timecapsule.user.entity.Auth;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.jwt.JwtTokenProvider;
import com.example.timecapsule.user.repository.AuthRepository;
import com.example.timecapsule.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;


    public UserResponseDto register(UserRequestDto userRequestDto) {
        User user = UserResponseDto.of(userRequestDto, passwordEncoder.encode(userRequestDto.getUserPw()));
        userRepository.save(user);
        return UserResponseDto.toUserResponse(user);
    }

    public User register(KakaoResponse kakaoResponse) {
        if (kakaoResponse.getKakao_account().getEmail() == null || kakaoResponse.getKakao_account().getEmail().equals(""))
            kakaoResponse.getKakao_account().setEmail(kakaoResponse.getId() + "@gmail.com");
        User user = KakaoResponse.of(kakaoResponse, passwordEncoder.encode(kakaoResponse.getId() + "kakao"));
        userRepository.save(user);
        return user;
    }

    public void isUserIdDuplicated(String userId) {
        log.info("UserService.isUserIdDuplicated");
        if (userRepository.existsByUserId(userId)) {
            throw new DUPLICATEDATEXCEPTION();
        }
    }

    public void isUserNicknameDuplicated(String userNickname) {
        if (userRepository.existsByUserNickname(userNickname)) {
            throw new DUPLICATEDATEXCEPTION();
        }
    }

    public TokenResponseDto login(UserRequestDto userRequestDto) {
        User user = userRepository.findUserByUserId(userRequestDto.getUserId()).orElseThrow(IDEXCEPTION::new);
        if (!passwordEncoder.matches(userRequestDto.getUserPw(), user.getUserPw())) {
            throw new PASSWORDEXCEPTION();
        }
        return issueToken(user.getUserId());
    }
    public TokenResponseDto issueToken(final String userid){
        String accessToken = jwtTokenProvider.createAccessToken(userid);
        String refreshToken = jwtTokenProvider.createRefreshToken(userid);

        Auth authFromRepository = authRepository.findAuthByUserId(userid);
        
        if (authFromRepository != null) {
            authRepository.updateAuth(userid, refreshToken);
        }
        else{
            Auth auth = Auth.builder()
                    .userId(userid)
                    .refreshToken(refreshToken)
                    .build();
            authRepository.save(auth);
        }

        User userFromRepository = userRepository.findUserByUserId(userid).orElseThrow(IDEXCEPTION::new);

        return TokenResponseDto.builder()
                .userId(userFromRepository.getId())
                .userEmail(userFromRepository.getUserEmail())
                .userNickname(userFromRepository.getUserNickname())
                .ACCESS_TOKEN(accessToken)
                .REFRESH_TOKEN(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    public User findUserByAccessToken(String accessToken) {
//        jwtTokenProvider.getUserInfoFromToken(accessToken);
//        Auth auth=authRepository.findAuthByAccessToken(accessToken);
        log.info("유저의 정보는 : {}", jwtTokenProvider.getUserInfoFromToken(accessToken));
        return userRepository.findUserByUserId(jwtTokenProvider.getUserInfoFromToken(accessToken)).orElseThrow(NOTFOUNDEXCEPTION::new);
    }

    @Transactional(readOnly = true)
    public User findUserByUserEmail(String email) {
        return userRepository.findUserByUserEmail(email).orElse(null);
    }

    public void deleteUser(Long userId, String accessToken) {
        //TODO 자바는 카멜케이스 명명규칙
        User nowuser = findUserByAccessToken(accessToken);
        //TODO 이런 분기는 좋지 않음 최대한 ifelse를 없애기
        if (!nowuser.getId().equals(userId)) {
            throw new IDEXCEPTION();
        }
        userRepository.deleteById(userId);
    }

    public void logout(String userId){
        //token 무효화
    }
    @Transactional
    public void changeNickname(String accessToken,String userNickname) {
        User user=findUserByAccessToken(accessToken);
        isUserNicknameDuplicated(userNickname);
        user.changeName(userNickname);
    }

}
