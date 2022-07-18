package com.example.timecapsule.user.controller;

import com.example.timecapsule.main.common.CommonResult;
import com.example.timecapsule.main.common.SingleResult;
import com.example.timecapsule.main.common.service.ResponseService;
import com.example.timecapsule.user.dto.response.TokenResponseDto;
import com.example.timecapsule.user.dto.request.UserRequestDto;
import com.example.timecapsule.user.dto.response.UserResponseDto;
import com.example.timecapsule.user.jwt.JwtTokenProvider;
import com.example.timecapsule.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping()
    public ResponseEntity<SingleResult<UserResponseDto>> saveUser(@RequestBody UserRequestDto userRequestDto){
        return new ResponseEntity<>(responseService.getSingleResult(userService.register(userRequestDto)), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<SingleResult<TokenResponseDto>> login(@RequestBody UserRequestDto userRequestDto) throws Exception{
        return new ResponseEntity<>(responseService.getSingleResult(userService.login(userRequestDto)), HttpStatus.CREATED);
    }

    @GetMapping("/userIdCheck")
    public ResponseEntity<CommonResult> userIdDuplicationCheck(@RequestParam String userId){
        userService.isUserIdDuplicated(userId);
        return new ResponseEntity<>(responseService.getSuccessResult(),HttpStatus.OK);
    }

    @GetMapping("/userNicknameCheck")
    public ResponseEntity<CommonResult> userNicknameDuplicationCheck(@RequestParam String userNickname){
        userService.isUserNicknameDuplicated(userNickname);
        return new ResponseEntity<>(responseService.getSuccessResult(),HttpStatus.OK);
    }

//    @GetMapping("/logout")
//    public ResponseEntity<CommonResult> logout(){
//
//    }

    @GetMapping("/test")
    public String test(@RequestHeader Map<String, String> headers){
        System.out.println("headers = " + headers);
        String token = headers.get("x-auth-token");
        System.out.println("token = " + token);
        String userIdFromToken = jwtTokenProvider.getUserInfoFromToken(token);
        System.out.println("userIdFromToken = " + userIdFromToken);
        return "hi";
    }
    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<CommonResult> deleteId(
            @RequestHeader("X-AUTH-TOKEN") String accessToken,
            @PathVariable Long user_id
    ){
        userService.deleteUser(user_id,accessToken);
        return new ResponseEntity<>(responseService.getSuccessResult(), HttpStatus.OK);
    }
    @PatchMapping("/nickname")
    public ResponseEntity<CommonResult> updateNickname(@RequestHeader("X-AUTH-TOKEN")String accessToken,@RequestParam String userNickname){
        userService.changeNickname(accessToken,userNickname);
        return new ResponseEntity<>(responseService.getSuccessResult(),HttpStatus.OK);
    }

}
