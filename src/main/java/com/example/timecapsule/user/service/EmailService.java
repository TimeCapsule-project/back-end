package com.example.timecapsule.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailService {
    //Todo 이메일 인증 코드가 static이여서 어떤 이메일을 입력해도 같은 값만 출력
    private final JavaMailSender emailSender;

    public static final String emailAuthCode = createKey();
    @Value("${AdminMail.id}")
    private String EMAILID;
    private String TEAMNAME = "황금두더지";
    private String CHARSET = "utf-8";
    private String SUBTYPE = "html";

    private MimeMessage createMessage(String recipient) throws Exception {
        log.info("TO : " + recipient);
        log.info("AUTH CODE : " + emailAuthCode);
        MimeMessage message = emailSender.createMimeMessage();

        //String code = createCode(emailAuthCode);
        message.addRecipients(MimeMessage.RecipientType.TO, recipient); //보내는 대상
        message.setSubject("황금두더지 확인 코드: " + emailAuthCode); //제목

        String msg = "";
        //msg += "<img width=\"120\" height=\"36\" style=\"margin-top: 0; margin-right: 0; margin-bottom: 32px; margin-left: 0px; padding-right: 30px; padding-left: 30px;\" src=\"https://slack.com/x-a1607371436052/img/slack_logo_240.png\" alt=\"\" loading=\"lazy\">";
        msg += "<h1 style=\"font-size: 25px; padding-right: 30px; padding-left: 30px;\">이메일 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 황금두더지 가입 창에 입력하세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += emailAuthCode;
        msg += "</td></tr></tbody></table></div>";
        //msg += "<a href=\"https://slack.com\" style=\"text-decoration: none; color: #434245;\" rel=\"noreferrer noopener\" target=\"_blank\">황금두더지</a>";

        message.setText(msg, CHARSET, SUBTYPE); //내용
        message.setFrom(new InternetAddress(EMAILID, TEAMNAME)); //보내는 사람

        return message;
    }

    // 인증코드 만들기
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();
        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

    @Async
    public void sendSimpleMessage(String recipient) throws Exception {
        MimeMessage message = createMessage(recipient);
        try {
            emailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

}
