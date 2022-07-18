package com.example.timecapsule.user.jwt;

import com.example.timecapsule.user.repository.AuthRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    @Value("${secret.access}")
    private String SECRET_KEY;// = "sec";
    @Value("${secret.refresh}")
    private String REFRESH_KEY;// = "ref";

    private final long ACCESS_TOKEN_VALID_TIME = 60 * 60 * 24 * 1000L;   // 24시간
    private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 7 * 1000L;   // 1주

    private final UserDetailsService userDetailsService;
    private AuthRepository authRepository;

    @PostConstruct
    protected void init() {
        System.out.println("SECRET_KEY: " + SECRET_KEY);
        System.out.println("REFRESH_KEY = " + REFRESH_KEY);
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        REFRESH_KEY = Base64.getEncoder().encodeToString(REFRESH_KEY.getBytes());
    }

    public String createAccessToken(String userId) {
        Claims claims = Jwts.claims();//.setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("userId", userId); //token을 userId로 만듦
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 사용할 암호화 알고리즘과
                .compact();
    }

    public String createRefreshToken(String userId) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId); //load userId
        Date now = new Date();
        Date expiration = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, REFRESH_KEY)
                .compact();
    }
//    public boolean validateToken(String jwtToken) {
//        Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
//        return !claims.getBody().getExpiration().before(new Date());
////        try {
////            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
////            //claims.getBody : {userId=lee, iat=1652106416, exp=1652108216}
////            return !claims.getBody().getExpiration().before(new Date());
////        }
////        catch (ExpiredJwtException e) {
////            //e.printStackTrace();
////            throw e;
////        }
//    }
    public Jws<Claims> validateToken(HttpServletRequest request, String jwtToken){
        Jws<Claims> claims = null;
//        try{
//            claims =  Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
//        }catch(ExpiredJwtException e){
//            request.setAttribute("exception", -2);
//        }catch(JwtException e){
//            request.setAttribute("exception", -4);
//        }
//        return claims;
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
    }

//    public String getUserInfoFromToken(String token) throws ExpiredJwtException, JwtException{
//        //log.info("body" + Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody());
//        //return (String) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("userId");
//        return (String) parseToken(token).getBody().get("userId");
//    }

    public String getUserInfoFromToken(String token){
        //log.info("body" + Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody());
        //return (String) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("userId");
        return (String) parseToken(token).getBody().get("userId");
    }

    public Jws<Claims> parseToken(String jwtToken){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserInfoFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

//    public TokenResponseDto reissueToken(TokenResponseDto tokenResponseDto, String token){
//        //do not need to reissue token
//        if(validateToken(tokenResponseDto.getACCESS_TOKEN())) {
//            throw new IllegalArgumentException();
//        }
//
//        String userIdFromToken = getUserInfoFromToken(token);
//
//        //only if access token is expired and validated
//        if(getUserInfoFromToken(tokenResponseDto.getACCESS_TOKEN()).equals(userIdFromToken)){
//            Auth authFromRepo = authRepository.findAuthByUserId(getUserInfoFromToken(userIdFromToken));
//            String refreshTokenFromRepo = authFromRepo.getRefreshToken();
//            //refresh token is validated
//            if (refreshTokenFromRepo.equals(tokenResponseDto.getREFRESH_TOKEN())){
//                String newRefreshToken = createRefreshToken(userIdFromToken);
//                authRepository.updateAuth(userIdFromToken, newRefreshToken);
//                return new TokenResponseDto(refreshTokenFromRepo, newRefreshToken);
//            }
//        }
//        return new TokenResponseDto("","");
//    }

}
