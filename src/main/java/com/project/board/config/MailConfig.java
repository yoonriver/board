package com.project.board.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    protected String HOST;

    @Value("${spring.mail.port}")
    protected int PORT;

    @Value("${spring.mail.protocol}")
    protected String PROTOCOL;

    @Value("${spring.mail.username}")
    protected String USER_NAME;

    @Value("${spring.mail.password}")
    protected String PASSWORD;

    @Bean
    public JavaMailSender mailSender() throws IOException {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(HOST);
        mailSender.setPort(PORT);
        mailSender.setProtocol(PROTOCOL);
        mailSender.setDefaultEncoding(StandardCharsets.UTF_8.name());
        mailSender.setUsername(USER_NAME);
        mailSender.setPassword(PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.debug", "true");

        return mailSender;

    }

}
