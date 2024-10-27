package process.daemon.goods;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Slf4j
@Component
public class GoodsSendTask implements Runnable {

    private final BlockingQueue<Integer> queue;

    @Autowired
    public GoodsSendTask(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        log.info("GoodsSendTask started");

        try {
            while (!Thread.currentThread().isInterrupted()) {
                // 상품 발송
                Integer goodsNo = queue.take();
                log.info("Send goodsNo: {}", goodsNo);

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.info("GoodsSendTask interrupted", e);
        }
    }
}
