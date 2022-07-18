package com.example.timecapsule.user.controller;


import com.example.timecapsule.user.service.AuthService;
import com.example.timecapsule.main.common.SingleResult;
import com.example.timecapsule.main.common.service.ResponseService;
import com.example.timecapsule.user.dto.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/account")
@CrossOrigin(value = {"*"}, maxAge = 6000)
public class KakaoLoginController {
    private final ResponseService responseService;
    private final AuthService authService;

    @GetMapping("/kakaologin")
    public ResponseEntity<SingleResult<TokenResponseDto>> getTokenAndJoinOrLogin(
            @RequestParam("token") String accessToken) {
        return new ResponseEntity<>(responseService.getSingleResult(authService.getKakaoLogin(accessToken)), HttpStatus.CREATED);
    }
}
