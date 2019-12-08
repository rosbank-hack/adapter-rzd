package ros.hack.rzd.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ros.hack.rzd.config.properties.KafkaProperties;

@Configuration
@EnableConfigurationProperties({
        KafkaProperties.class
})
public class PropertiesConfiguration { }
