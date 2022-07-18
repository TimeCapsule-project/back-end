package com.example.timecapsule.config;

import com.example.timecapsule.exception.*;
import com.example.timecapsule.main.common.CommonResponse;
import com.example.timecapsule.main.common.CommonResult;
import com.example.timecapsule.main.common.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {
    private final ResponseService responseService;
    @ExceptionHandler(NOTFOUNDEXCEPTION.class)
    public ResponseEntity<CommonResult> NotFoundException(NOTFOUNDEXCEPTION e) {
        CommonResult commonResult = responseService.getFailResult(CommonResponse.NOTFOUND);
        return new ResponseEntity<>(commonResult, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(NOTFOUNDUSEREXCEPTION.class)
    public ResponseEntity<CommonResult> NotFoundUserException(NOTFOUNDUSEREXCEPTION e) {
        CommonResult commonResult = responseService.getFailResult(CommonResponse.NOTFOUND);
        return new ResponseEntity<>(commonResult, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(NOT.class)
    public ResponseEntity<CommonResult> NOT(NOT e) {
        CommonResult commonResult = responseService.getFailResult(CommonResponse.NOT);
        return new ResponseEntity<>(commonResult, HttpStatus.EXPECTATION_FAILED);
    }
    @ExceptionHandler(DUPLICATEDATEXCEPTION.class)
    public ResponseEntity<CommonResult> DuplicateDATAException(DUPLICATEDATEXCEPTION e) {
        CommonResult commonResult = responseService.getFailResult(CommonResponse.DuplicateDATA);
        return new ResponseEntity<>(commonResult, HttpStatus.EXPECTATION_FAILED);
    }
    @ExceptionHandler(PASSWORDEXCEPTION.class)
    public ResponseEntity<CommonResult> PasswordException(PASSWORDEXCEPTION e) {
        CommonResult commonResult = responseService.getFailResult(CommonResponse.PASSWORD);
        return new ResponseEntity<>(commonResult, HttpStatus.EXPECTATION_FAILED);
    }
    @ExceptionHandler(IDEXCEPTION.class)
    public ResponseEntity<CommonResult> PasswordException(IDEXCEPTION e) {
        CommonResult commonResult = responseService.getFailResult(CommonResponse.ID);
        return new ResponseEntity<>(commonResult, HttpStatus.EXPECTATION_FAILED);
    }
    @ExceptionHandler(UNAUTHORIZEDEXCEPTION.class)
    public ResponseEntity<CommonResult> PasswordException(UNAUTHORIZEDEXCEPTION e) {
        CommonResult commonResult = responseService.getFailResult(CommonResponse.UNAUTHORIZED);
        return new ResponseEntity<>(commonResult, HttpStatus.EXPECTATION_FAILED);
    }

}
