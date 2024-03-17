package com.n11.restaurantservice.general;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitProducerService {

    private final RabbitTemplate rabbitTemplate;

    public void sendErrorMessage(String message) {
        rabbitTemplate.convertAndSend("exchange", "errorRouting", message);
    }

    public void sendInfoMessage(String message) {
        rabbitTemplate.convertAndSend("exchange", "infoRouting", message);
    }
}
