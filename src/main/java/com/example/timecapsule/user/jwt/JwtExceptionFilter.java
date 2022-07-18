package com.example.timecapsule.user.jwt;

import com.example.timecapsule.main.common.CommonResponse;
import com.example.timecapsule.main.common.CommonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtExceptionFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try{
            filterChain.doFilter(servletRequest, servletResponse);

        }catch(ExpiredJwtException e){
            HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setCharacterEncoding("UTF-8");
            CommonResult result = new CommonResult();
            result.setSuccess(false);
            result.setCode(CommonResponse.JWTEXPIRED.getCode());
            result.setMsg(CommonResponse.JWTEXPIRED.getMsg());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(httpServletResponse.getWriter(), result);
        }catch(JwtException e){
            HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setCharacterEncoding("UTF-8");
            CommonResult result = new CommonResult();
            result.setSuccess(false);
            result.setCode(CommonResponse.JWTINVALID.getCode());
            result.setMsg(CommonResponse.JWTINVALID.getMsg());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(httpServletResponse.getWriter(), result);
        }catch(NullPointerException e){
            HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setCharacterEncoding("UTF-8");
            CommonResult result = new CommonResult();
            result.setSuccess(false);
            result.setCode(CommonResponse.JWTNULL.getCode());
            result.setMsg(CommonResponse.JWTNULL.getMsg());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(httpServletResponse.getWriter(), result);
        }

    }
}
