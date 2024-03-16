package com.n11.logger.service;

import com.n11.logger.entity.ErrorLog;
import com.n11.logger.repository.ErrorLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQConsumerService {

    private final RabbitTemplate rabbitTemplate;
    private final ErrorLogRepository errorLogRepository;

    @RabbitListener(queues = "queueName")
    public void consume(String message){

        log.info("consume started!");

        ErrorLog errorLog = new ErrorLog();
        errorLog.setDate(LocalDateTime.now());
        errorLog.setMessage(message);
        errorLog.setDescription("Error");

        errorLogRepository.save(errorLog);

        log.info("consume finished!");
    }

}
