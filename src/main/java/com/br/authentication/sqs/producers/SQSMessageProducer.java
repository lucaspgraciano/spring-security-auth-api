package com.br.authentication.sqs.producers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SQSMessageProducer {

    @Value("${cloud.aws.sqs.name}")
    private String queueName;

    private final QueueMessagingTemplate queueMessagingTemplate;

    public SQSMessageProducer(QueueMessagingTemplate queueMessagingTemplate) {
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    public <T> void send(T message, Map<String, Object> headers) {
        if (message == null) return;
        queueMessagingTemplate.convertAndSend(queueName, message, headers);
    }
}
