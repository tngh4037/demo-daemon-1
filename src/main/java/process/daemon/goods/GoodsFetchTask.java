package process.daemon.goods;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Slf4j
@Component
public class GoodsFetchTask implements Runnable {

    private final BlockingQueue<Integer> queue;

    @Autowired
    public GoodsFetchTask(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        log.info("GoodsFetchTask started");

        try {
            int count = 0;

            while (!Thread.currentThread().isInterrupted()) {
                count++;

                // 상품 조회
                Integer goodsNo = count;
                log.info("Fetch goodsNo: {}", goodsNo);
                queue.put(goodsNo);

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.info("GoodsFetchTask interrupted", e);
        }
    }
}
