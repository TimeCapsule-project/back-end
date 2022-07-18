package com.example.timecapsule.user.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
        String tokenFromHeader = httpServletRequest.getHeader("X-AUTH-TOKEN");
        log.info("TOKEN FROM HEADER:" + tokenFromHeader);

        //토큰 유효
//        try{
//            if (tokenFromHeader != null && jwtTokenProvider.validateToken(tokenFromHeader)){
//                Authentication authentication = jwtTokenProvider.getAuthentication(tokenFromHeader);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }catch(ExpiredJwtException e){
//            System.out.println("here");
//            throw e;
//        }
        Jws<Claims> claims = null;
        String userIdFromToken = "";

        if(tokenFromHeader!=null) {
            claims = jwtTokenProvider.validateToken(httpServletRequest, tokenFromHeader);
            userIdFromToken = jwtTokenProvider.getUserInfoFromToken(tokenFromHeader);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
