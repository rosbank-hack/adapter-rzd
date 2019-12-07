package ros.hack.rzd.service.impl;

import com.github.voteva.Operation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ros.hack.rzd.config.KafkaProperties;
import ros.hack.rzd.service.ConsumerService;
import ros.hack.rzd.service.ProducerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static ros.hack.rzd.consts.Constants.DESCRIPTION;
import static ros.hack.rzd.consts.Constants.SERVICE_NAME;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final KafkaProperties kafkaProperties;
    private final ProducerService producerService;

    @Override
    @Transactional
    @KafkaListener(topics = "${kafka.payment-topic}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(@NonNull List<Operation> operations) {
        operations.forEach(operation -> {
            log.info(operation.toString());
            producerService.send(kafkaProperties.getOperationTopic(), addBonuses(operation));
        });
    }

    private Operation addBonuses(@NonNull Operation operation) {
        com.github.voteva.Service rzd = new com.github.voteva.Service();
        if (operation.getServices() != null
                && operation.getServices().get(SERVICE_NAME) != null) {
            rzd = operation.getServices().get(SERVICE_NAME);
        }

        Map<String, String> request = new HashMap<>();
        if (rzd.getRequest() != null) {
            request = rzd.getRequest();
        }
        Map<String, String> response = request;

        response.put(DESCRIPTION, "С картой РЖД-Росбанк вы могли получить " + getRandomBonus() + "бонусов.");

        rzd.setRequest(request);
        rzd.setResponse(response);

        operation.getServices().put(SERVICE_NAME, rzd);
        return operation;
    }

    private Integer getRandomBonus() {
        Random random = new Random();
        return random.nextInt(151) + 50;
    }
}
