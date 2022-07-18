package com.example.timecapsule.user.controller;

import com.example.timecapsule.main.common.CommonResult;
import com.example.timecapsule.main.common.SingleResult;
import com.example.timecapsule.main.common.service.ResponseService;
import com.example.timecapsule.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/email")
public class EmailController {
    private final EmailService emailService;
    private final ResponseService responseService;

    @GetMapping() // 이메일 인증 코드 보내기
    public ResponseEntity<CommonResult> emailAuth(@RequestParam String email) throws Exception {
        emailService.sendSimpleMessage(email);
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }

    @GetMapping("/verifyCode") // 이메일 인증 코드 검증
    public ResponseEntity<CommonResult> verifyCode(@RequestParam String code) {
        if(emailService.emailAuthCode.equals(code)) {
            return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
        }
        return new ResponseEntity<>(responseService.getFailResult(), HttpStatus.BAD_REQUEST);
    }
}
