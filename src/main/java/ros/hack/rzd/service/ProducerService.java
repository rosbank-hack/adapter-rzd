package ros.hack.rzd.service;

import com.github.voteva.Operation;

public interface ProducerService {
    void send(String topic, Operation operation);
}