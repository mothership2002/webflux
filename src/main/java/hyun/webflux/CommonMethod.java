package hyun.webflux;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonMethod {

    public static void getThread() {
        log.info("Thread : {}", Thread.currentThread().getName());
    }
}
