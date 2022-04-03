package com.example.timecapsule.user.controller;

import com.example.timecapsule.account.dto.response.MyAccountResponse;
import com.example.timecapsule.main.common.CommonResult;
import com.example.timecapsule.main.common.SingleResult;
import com.example.timecapsule.main.common.service.ResponseService;
import com.example.timecapsule.user.dto.TokenResponseDto;
import com.example.timecapsule.user.dto.UserRequestDto;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@Slf4j
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<String> saveUser(@RequestBody UserRequestDto userRequestDto){
        userService.register(userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody UserRequestDto userRequestDto) throws Exception{
        return ResponseEntity.ok(userService.login(userRequestDto));
    }

    @GetMapping("/join/userIdCheck")
    public ResponseEntity<String> userIdDuplicationCheck(@RequestParam String userId){
        if (userService.isUserIdDuplicated(userId)){
            return ResponseEntity.status(HttpStatus.OK).body("user id already exists");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body("available user id");
        }
    }

    @GetMapping("/join/userNicknameCheck")
    public ResponseEntity<String> userNicknameDuplicationCheck(@RequestParam String userNickname){
        if (userService.isUserNicknameDuplicated(userNickname)){
            return ResponseEntity.status(HttpStatus.OK).body("user nickname already exists");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body("available user nickname");
        }
    }

}
