package com.bazigar.bulandawaaz.email;

import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailSender mailSender1;

    public Response sendEmail(String toEmail, String body, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom("bazigarTesting@gmail.com");
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        return new Response(
                subject+" successfully sent!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

    public Response prepareAndSend(String toEmail, String body, String subject) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("bazigarTesting@gmail.com");
            messageHelper.setTo(toEmail);
            messageHelper.setSubject(subject);
//            String htmlMsg = "<h3>Hello World!</h3>";
            messageHelper.setText(body, true);
        };
        try {
            mailSender.send(messagePreparator);
            return new Response(
                    subject+" successfully sent!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        } catch (MailException e) {
            return new Response(
                    subject+e.getMessage(),
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }





}
