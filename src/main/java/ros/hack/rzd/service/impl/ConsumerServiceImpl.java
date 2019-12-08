package ros.hack.rzd.service.impl;

import com.github.voteva.Operation;
import com.github.voteva.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;
import ros.hack.rzd.config.properties.KafkaProperties;
import ros.hack.rzd.service.ConsumerService;
import ros.hack.rzd.service.ProducerService;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Random;

import static com.google.common.collect.Maps.newHashMap;
import static ros.hack.rzd.consts.Constants.DESCRIPTION;
import static ros.hack.rzd.consts.Constants.SERVICE_NAME;
import static ros.hack.rzd.utils.JsonParser.parse;

@Slf4j
@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ConsumerServiceImpl
        implements ConsumerService<String, String> {

    private final KafkaProperties kafkaProperties;
    private final ProducerService producerService;

    @Override
    @Transactional
    @KafkaListener(topics = "${kafka.payment-topic}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(@Nonnull ConsumerRecord<String, String> consumerRecord) {
        log.info(consumerRecord.toString());
        producerService.send(kafkaProperties.getOperationTopic(), addBonuses(parse(consumerRecord.value())));
    }

    @Nonnull
    private Operation addBonuses(@Nonnull Operation operation) {
        Service rzd = new Service();
        if (operation.getServices() != null && operation.getServices().get(SERVICE_NAME) != null) {
            rzd = operation.getServices().get(SERVICE_NAME);
        }

        Map<String, String> request = newHashMap();
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

    @Nonnull
    private Integer getRandomBonus() {
        Random random = new Random();
        return random.nextInt(151) + 50;
    }
}
