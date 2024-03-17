package com.n11.logger.service;

import com.n11.logger.entity.Log;
import com.n11.logger.repository.LogRepository;
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
    private final LogRepository logRepository;

    @RabbitListener(queues = "errorQueue")
    public void consumeError(String message){

        log.info("Error consume started!");

        Log errorLog = new Log();
        errorLog.setDate(LocalDateTime.now());
        errorLog.setMessage(message);
        errorLog.setDescription("Error");

        logRepository.save(errorLog);

        log.info("Error consume finished!");
    }

    @RabbitListener(queues = "infoQueue")
    public void consumeInfo(String message){

        log.info("Info consume started!");

        Log infoLog = new Log();
        infoLog.setDate(LocalDateTime.now());
        infoLog.setMessage(message);
        infoLog.setDescription("Info");

        logRepository.save(infoLog);

        log.info("Info consume finished!");
    }

}
