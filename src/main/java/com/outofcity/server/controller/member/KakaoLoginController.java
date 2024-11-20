package com.outofcity.server.controller.member;

import com.outofcity.server.dto.member.kakaologin.response.SuccessLoginResponseDto;
import com.outofcity.server.global.exception.dto.SuccessStatusResponse;
import com.outofcity.server.global.exception.dto.oauth.KakaoUserInfoResponseDto;
import com.outofcity.server.global.exception.message.SuccessMessage;
import com.outofcity.server.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/callback")
    public ResponseEntity<SuccessStatusResponse<SuccessLoginResponseDto>> callback(@RequestParam("code") String code) {
        // 1. 코드로 Access Token 발급
        String accessToken = kakaoLoginService.getAccessTokenFromKakao(code);
        log.info("accessToken: {}", accessToken);
        // 2. Access Token으로 사용자 정보 가져오기
        KakaoUserInfoResponseDto userInfo = kakaoLoginService.getUserInfo(accessToken);

        // 3. 사용자 로그인 또는 회원가입 처리
        SuccessLoginResponseDto successLoginResponseDto = kakaoLoginService.findUser(userInfo, accessToken);

        // 5. 메인 페이지로 리다이렉트
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(
                        SuccessMessage.SIGNIN_SUCCESS, successLoginResponseDto
                )
        );
    }
}