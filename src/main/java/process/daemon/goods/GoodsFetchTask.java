package process.daemon.goods;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

// TODO::// 각 Task 에서 비즈니스 예외 발생시 AOP 적용해서 slack 알람 받도록 적용
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

            // while (!Thread.currentThread().isInterrupted()) {
            while (!Thread.interrupted()) { // 자신이 인터럽트 상태인지 체크 및 인터럽트 상태(true)라면 다시 정상으로 상태 변환(false)
                count++;

                // 상품 조회 (ex. DB list fetch)
                /*
                List<GoodsDto> fetchTarget = repository.getGoodsList();
                if (fetchTarget.empty()) {
                    // 계속해서 DB Fetch 하지 않음
                    Thread.yield(); or Thread.sleep(10);
                    continue;
                }
                */

                // 반복문을 통한 fetchTarget 요소 큐 삽입
                Integer goodsNo = count;
                log.info("Fetch goodsNo: {}", goodsNo);
                queue.put(goodsNo); // blocking
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.info("GoodsFetchTask interrupted", e);
        }
    }
}

// TODO :: try-catch는 blocking 메서드가 있는곳으로만 한정할지 고민.
//   ㄴ 스레드와 생명주기2 - 19페이지 ( 프린터 예제3 - 인터럽트 코드 개선 ) 보기
