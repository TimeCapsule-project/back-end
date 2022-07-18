package com.example.timecapsule.search.service;

import com.example.timecapsule.search.dto.UserSearchResponseDto;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.repository.UserRepository;
import com.example.timecapsule.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {
    private final UserRepository userRepository;
    private final UserService userService;

    public List<UserSearchResponseDto> getAllUsersWithKeyword(String keyword){
        List<User> allUserWithKeyword = userRepository.findByUserNicknameContainsIgnoreCase(keyword.replace(" ",""));
        List<UserSearchResponseDto> returnList = new ArrayList<>();
        for (User user : allUserWithKeyword){
            log.info(user.toString());
            if (user.getUserNickname().contains(keyword) && user.isUserSearchEnabled()) {
                returnList.add(UserSearchResponseDto.toUserSearchResponseDto(user));
            }
        }
        return returnList;
    }
    //Stream 사용
    @Transactional(readOnly = true)
    public List<UserSearchResponseDto> searchNickname(String keyword){
        log.info("Search Service: searchNickname success");
        return userRepository.findByUserNicknameContainsIgnoreCase(keyword.replace(" ","")).stream()
                .filter(User::isUserSearchEnabled)
                .map(UserSearchResponseDto::toUserSearchResponseDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public void getUserSearchEnabled(final String accessToken){
        User userFromToken = userService.findUserByAccessToken(accessToken);
        userFromToken.changeUserSearchAble();
    }

}
