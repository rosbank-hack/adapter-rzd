package ros.hack.rzd.service;

import com.github.voteva.Operation;

import java.util.List;

public interface ConsumerService {
    void consume(List<Operation> items);
}