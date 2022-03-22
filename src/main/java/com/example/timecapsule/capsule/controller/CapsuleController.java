package com.example.timecapsule.capsule.controller;

import com.example.timecapsule.capsule.dto.request.CapsuleRequest;
import com.example.timecapsule.capsule.dto.response.CapsuleResponse;
import com.example.timecapsule.capsule.dto.response.OpenCapsuleResponse;
import com.example.timecapsule.capsule.entity.Capsule;
import com.example.timecapsule.capsule.service.CapsuleService;
import com.example.timecapsule.main.common.CommonResponse;
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
    @PostMapping
    public ResponseEntity<SingleResult<CapsuleResponse>> postCapsule(
            @RequestHeader("Authorization") String accessToken,
            @RequestPart(value = "capsule") CapsuleRequest capsuleRequest
    ){
        String[] splitToken = accessToken.split(" ");
        CapsuleResponse capsule=capsuleService.createCapsule(splitToken[1],capsuleRequest);
        return new ResponseEntity<>(responseService.getSingleResult(capsule), HttpStatus.CREATED);
    }
    //랜덤 닉네임 생성요청
    @GetMapping("/nickname")
    public ResponseEntity<ListResult<String>> getNicknmae() {
    return new ResponseEntity<>(responseService.getListResult(capsuleService.getRandomNickname()),HttpStatus.OK);
    }
    //캡슐 디테일 확인
    @GetMapping("detail/{capsule_id}")
    public ResponseEntity<SingleResult<CapsuleResponse>> getMyCapsule(@PathVariable Long capsule_id){
        return new ResponseEntity<>(responseService.getSingleResult(capsuleService.getDetailCapsule(capsule_id)),HttpStatus.OK);
    }
    //현재 사용자가 받은 캡슐 불러오기
    @GetMapping("/main")
    public ResponseEntity<ListResult<CapsuleResponse>> getMyCapsule(
            @RequestHeader("Authorization") String accessToken
    ){
        String[] splitToken = accessToken.split(" ");
        return new ResponseEntity<>(responseService.getListResult( capsuleService.getListCapsule(splitToken[1])),HttpStatus.OK);
    }
    //캡슐 삭제하기
    @DeleteMapping("/{capsule_id}")
    public ResponseEntity<CommonResult> deleteCapsule(
            @PathVariable Long capsule_id,
            @RequestHeader("Authorization") String accessToken)
    {
        String[] splitToken = accessToken.split(" ");
        capsuleService.deleteCapsule(capsule_id,splitToken[1]);
        return new ResponseEntity<>(responseService.getSuccessResult(),HttpStatus.OK);
    }
    //보낸 캡슐 읽었는지만 확인
    @GetMapping("/opening")
    public ResponseEntity<ListResult<OpenCapsuleResponse>> getOpenInfoCapsule(@RequestHeader("Authorization") String accessToken){
        String[] splitToken = accessToken.split(" ");
        return new ResponseEntity<>(responseService.getListResult(capsuleService.OpenedCapsule(splitToken[1])),HttpStatus.OK);
    }
}
