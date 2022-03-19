package com.example.timecapsule.capsule.controller;

import com.example.timecapsule.capsule.dto.request.CapsuleRequest;
import com.example.timecapsule.capsule.dto.response.SendCapsuleResponse;
import com.example.timecapsule.capsule.entity.Capsule;
import com.example.timecapsule.capsule.service.CapsuleService;
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
    public ResponseEntity<SingleResult<SendCapsuleResponse>> postCapsule(
            @RequestHeader("Authorization") String accessToken,
            @RequestPart(value = "capsule") CapsuleRequest capsuleRequest
    ){
        String[] splitToken = accessToken.split(" ");
        SendCapsuleResponse capsule=capsuleService.createCapsule(splitToken[1],capsuleRequest);
        return new ResponseEntity<>(responseService.getSingleResult(capsule), HttpStatus.CREATED);
    }

    //랜덤 닉네임 생성요청
    @GetMapping("/nickname")
    public ResponseEntity<ListResult<String>> getNicknmae() {
    return new ResponseEntity<>(responseService.getListResult(capsuleService.getRandomNickname()),HttpStatus.OK);
    }


    @GetMapping("detail/{capsule_id}")
    public ResponseEntity<SingleResult<Capsule>> getMyCapsule(@PathVariable Long capsule_id){
        return new ResponseEntity<>(responseService.getSingleResult(capsuleService.getDetailCapsule(capsule_id)),HttpStatus.OK);
    }
}