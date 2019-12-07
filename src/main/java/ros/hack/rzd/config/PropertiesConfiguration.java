package ros.hack.rzd.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        KafkaProperties.class
})
public class PropertiesConfiguration {
}
