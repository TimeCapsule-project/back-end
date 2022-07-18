package com.example.timecapsule.capsule.controller;

import com.example.timecapsule.capsule.dto.request.AnywhereCapsuleRequest;
import com.example.timecapsule.capsule.dto.request.LocationRequest;
import com.example.timecapsule.capsule.dto.request.SpecialCapsuleRequest;
import com.example.timecapsule.capsule.dto.response.SpecialCapsuleResponse;
import com.example.timecapsule.capsule.dto.response.OpenCapsuleResponse;
import com.example.timecapsule.capsule.service.CapsuleService;
import com.example.timecapsule.main.common.CommonResult;
import com.example.timecapsule.main.common.ListResult;
import com.example.timecapsule.main.common.SingleResult;
import com.example.timecapsule.main.common.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/capsule")
public class CapsuleController {
    private final CapsuleService capsuleService;
    private final ResponseService responseService;

    //캡슐 등록
    @PostMapping("/special")
    public ResponseEntity<SingleResult<SpecialCapsuleResponse>> postSpecialCapsule(
            @RequestHeader("X-AUTH-TOKEN") String accessToken,
            @RequestBody SpecialCapsuleRequest capsuleRequest
    ){
        return new ResponseEntity<>(responseService.getSingleResult(capsuleService.createCapsule(accessToken,capsuleRequest)), HttpStatus.CREATED);
    }
    @PostMapping("/anywhere")
    public ResponseEntity<SingleResult<SpecialCapsuleResponse>> postAnywhereCapsule(
            @RequestHeader("X-AUTH-TOKEN") String accessToken,
            @RequestBody AnywhereCapsuleRequest capsuleRequest
    ){
        return new ResponseEntity<>(responseService.getSingleResult(capsuleService.createCapsule(accessToken,capsuleRequest)), HttpStatus.CREATED);
    }

    //랜덤 닉네임 생성요청
    @GetMapping("/nickname")
    public ResponseEntity<SingleResult<String>> getNickname() {
    return new ResponseEntity<>(responseService.getSingleResult(capsuleService.getRandomNickname()),HttpStatus.OK);
    }

    //캡슐 디테일 확인
    @GetMapping("detail/{capsule_id}")
    public ResponseEntity<SingleResult<SpecialCapsuleResponse>> getMyCapsule( @RequestHeader("X-AUTH-TOKEN") String accessToken,
                                                                              @PathVariable Long capsule_id){
        return new ResponseEntity<>(responseService.getSingleResult(capsuleService.getDetailCapsule(accessToken,capsule_id)),HttpStatus.OK);
    }
    //현재 사용자가 받은 캡슐 불러오기
    @GetMapping("/main")
    public ResponseEntity<ListResult<SpecialCapsuleResponse>> getMyCapsule(
            @RequestHeader("X-AUTH-TOKEN") String accessToken
    ){
        return new ResponseEntity<>(responseService.getListResult( capsuleService.getListCapsule(accessToken)),HttpStatus.OK);
    }
    //캡슐 삭제하기
    @DeleteMapping("/{capsule_id}")
    public ResponseEntity<CommonResult> deleteCapsule(
            @PathVariable Long capsule_id,
            @RequestHeader("X-AUTH-TOKEN") String accessToken)
    {
        int code=capsuleService.deleteCapsule(capsule_id,accessToken);
        if(code ==200)
            return new ResponseEntity<>(responseService.getSuccessResult(),HttpStatus.OK);
        return new ResponseEntity<>(responseService.getFailResult(),HttpStatus.BAD_REQUEST);
    }
    //내가 보낸 캡슐 읽었는지만 확인
    @GetMapping("/opening")
    public ResponseEntity<ListResult<SpecialCapsuleResponse>> getOpenInfoCapsule(@RequestHeader("X-AUTH-TOKEN") String accessToken){
        return new ResponseEntity<>(responseService.getListResult(capsuleService.OpenedCapsule(accessToken)),HttpStatus.OK);
    }
    @GetMapping("/locationCheck")
    public ResponseEntity<SingleResult<Boolean>> checkLocation(
            @RequestBody LocationRequest locationRequest)
    {
        return new ResponseEntity<>(responseService.getSingleResult(capsuleService.check(locationRequest)),HttpStatus.OK);
    }

}
