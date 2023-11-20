package com.br.authentication.sqs.listeners;

import com.br.authentication.security.dtos.ResetEmailDto;
import com.br.authentication.security.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

@Component
public class SqsMessageListener {

    @Autowired
    private UserService userService;

    private static final ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json().build();

    @SqsListener("store-api")
    public void processMessage(String message) {
        try {
            ResetEmailDto dto = OBJECT_MAPPER.readValue(message, ResetEmailDto.class);

            this.userService.processRequestResetPassword(dto);

        } catch (Exception e) {
            throw new RuntimeException("Cannot process message from SQS", e);
        }
    }
}
