package ros.hack.bonuses.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.voteva.Operation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ros.hack.bonuses.service.ProducerService;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProducerServiceImpl implements ProducerService {

    private final KafkaTemplate<String, Operation> kafkaTemplate;

    @Override
    @SneakyThrows(JsonProcessingException.class)
    public void send(String topic, Operation operation) {
        log.debug(operation.toString());
        kafkaTemplate.send(topic, operation);
    }
}
