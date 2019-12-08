package ros.hack.rzd.service;

import com.github.voteva.Operation;

import javax.annotation.Nonnull;
import java.util.List;

public interface ConsumerService {
    void consume(@Nonnull List<Operation> items);
}