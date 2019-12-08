package ros.hack.rzd.service;

import com.github.voteva.Operation;

import javax.annotation.Nonnull;

public interface ProducerService {
    void send(@Nonnull String topic, @Nonnull Operation operation);
}