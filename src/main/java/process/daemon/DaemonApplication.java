package process.daemon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import process.daemon.goods.GoodsFetchTask;
import process.daemon.goods.GoodsSendTask;

import java.util.concurrent.*;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class DaemonApplication implements ApplicationRunner {

    private final ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(DaemonApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(2);

        es.execute(context.getBean(GoodsFetchTask.class));
        es.execute(context.getBean(GoodsSendTask.class));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            shutdownExecutorService(es);
        }));
    }

    private static void shutdownExecutorService(ExecutorService es) {
        es.shutdownNow(); // non-blocking

        try {
            if (!es.awaitTermination(10, TimeUnit.SECONDS)) { // blocking
                log.error("서비스가 종료되지 않았습니다.");
                // notify developer
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("서비스 종료 후 대기 중 인터럽트 발생", e);
        }
    }
}
