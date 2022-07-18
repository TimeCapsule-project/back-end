package com.example.timecapsule.capsule.service;

import com.example.timecapsule.capsule.dto.request.AnywhereCapsuleRequest;
import com.example.timecapsule.capsule.dto.request.LocationRequest;
import com.example.timecapsule.capsule.dto.request.SpecialCapsuleRequest;
import com.example.timecapsule.capsule.dto.response.ApiResponse;
import com.example.timecapsule.capsule.dto.response.SpecialCapsuleResponse;
import com.example.timecapsule.capsule.entity.Capsule;
import com.example.timecapsule.capsule.entity.CapsuleInfo;
import com.example.timecapsule.capsule.entity.Recipient;
import com.example.timecapsule.capsule.repository.CapsuleRepository;
import com.example.timecapsule.exception.NOTFOUNDEXCEPTION;
import com.example.timecapsule.main.common.Distance;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.repository.UserRepository;
import com.example.timecapsule.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CapsuleService {
    private final CapsuleRepository capsuleRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private static final String RANDOM_NICKNAME_API_URL = "http://bloodgang.shop:8080/api/v1/character";


    //캡슐 등록
    public SpecialCapsuleResponse createCapsule(final String accessToken, final SpecialCapsuleRequest capsuleRequest) {
        User user = userService.findUserByAccessToken(accessToken);
        User recipient=userRepository.findById(capsuleRequest.getRecipient()).orElseThrow(NOTFOUNDEXCEPTION::new);
        Recipient recipient1=new Recipient(recipient.getUserNickname(),recipient.getId());
        CapsuleInfo capsuleInfo=new CapsuleInfo(capsuleRequest.getContent(),capsuleRequest.getDuration(),
                capsuleRequest.setLocationFunc(capsuleRequest.getLatitude(),capsuleRequest.getLongitude())
                ,capsuleRequest.getNickname(),capsuleRequest.getCapsuleType());
        Capsule capsule = Capsule.builder()
                .capsuleInfo(capsuleInfo)
                .recipient(recipient1)
                .isOpened(false)
                .user(user)
                .build();
        capsuleRepository.save(capsule);
        return SpecialCapsuleResponse.toCapsuleResponse(capsule);
    }

    public SpecialCapsuleResponse createCapsule(final String accessToken, final AnywhereCapsuleRequest capsuleRequest) {
        User user = userService.findUserByAccessToken(accessToken);
        User recipient=userRepository.findById(capsuleRequest.getRecipient()).orElseThrow(NOTFOUNDEXCEPTION::new);
        Recipient recipient1=new Recipient(recipient.getUserNickname(),recipient.getId());
        CapsuleInfo capsuleInfo=new CapsuleInfo(capsuleRequest.getContent(),capsuleRequest.getDuration()
                ,capsuleRequest.getNickname(),capsuleRequest.getCapsuleType());
        Capsule capsule= Capsule.builder()
                .capsuleInfo(capsuleInfo)
                .recipient(recipient1)
                .isOpened(false)
                .user(user)
                .build();
        capsuleRepository.save(capsule);
        return SpecialCapsuleResponse.toCapsuleResponse(capsule);
    }

    public String getRandomNickname() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity(RANDOM_NICKNAME_API_URL, ApiResponse.class);
            log.info(Objects.requireNonNull(responseEntity.getBody()).getWord().get(0));
            return responseEntity.getBody().getWord().get(0);
        }catch (HttpClientErrorException e){
            throw new NOTFOUNDEXCEPTION();
        }catch (RestClientException e){
            e.printStackTrace();
            throw new NOTFOUNDEXCEPTION();
        }
    }

    public SpecialCapsuleResponse getDetailCapsule(final String accessToken,final Long capsule_id) {
        User user = userService.findUserByAccessToken(accessToken);
        Capsule capsule = capsuleRepository.findCapsuleByCapsuleId(capsule_id).orElseThrow(NOTFOUNDEXCEPTION::new);
        if (!capsule.getIsOpened()&&capsule.getRecipient().getRecipientId().equals(user.getId())) {
            capsule.setOpened(true);
            capsuleRepository.save(capsule);
        }
        return SpecialCapsuleResponse.toCapsuleResponse(capsule);
    }


    public List<SpecialCapsuleResponse> getListCapsule(final String accessToken) {
        User user = userService.findUserByAccessToken(accessToken);
        Recipient recipient=new Recipient(user.getUserId(),user.getId());
        return capsuleRepository.findCapsulesByRecipient_RecipientIdOrderByCreatedAtDesc(recipient.getRecipientId()).stream()
                .map(SpecialCapsuleResponse::toCapsuleResponse)
                .collect(Collectors.toList());
    }

    public int deleteCapsule(final Long capsuleId, final String accessToken) {
        Capsule nowCapsule = capsuleRepository.findById(capsuleId).orElseThrow(NOTFOUNDEXCEPTION::new);
        User nowuser = userService.findUserByAccessToken(accessToken);
        if (nowCapsule.getRecipient().equals(nowuser.getUserId())) {
            capsuleRepository.deleteById(capsuleId);
            //TODO 분기 다시 생각하기
            return 200;
        }
        return 401;

    }

    public List<SpecialCapsuleResponse> OpenedCapsule(final String accessToken) {
        User user = userService.findUserByAccessToken(accessToken);
        return capsuleRepository.findCapsulesBySenderId(user.getUserId()).stream()
                .map(SpecialCapsuleResponse::toCapsuleResponse)
                .collect(Collectors.toList());
    }

    public Boolean check(LocationRequest locationRequest) {
        Capsule capsule=capsuleRepository.findById(locationRequest.getCapsuleId()).orElseThrow(NOTFOUNDEXCEPTION::new);
        return Distance.isOpenable(locationRequest,capsule.getCapsuleInfo());
    }
}
