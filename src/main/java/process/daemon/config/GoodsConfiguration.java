package process.daemon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class GoodsConfiguration {

    @Bean
    public BlockingQueue<Integer> queue() {
        return new LinkedBlockingQueue<>();
    }
}
