package hyun.webflux.service;

import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.stream.IntStream;

@Service
public class HelloService {
    private static final String COLON = " : ";
    private static final String LINE_BREAK = "\n";

    public Long streamingError(Long seq, Boolean errorFlag, Long count) {
        if (errorFlag && (count / 2) == seq) {
            throw new RuntimeException();
        }
        return seq;
    }

    public String hello() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Thread.currentThread().getName();
    }

    public Iterator<String> iterator() {
        return IntStream.range(0, 100000)
                .mapToObj(integer -> getColonAndLineBreak(String.valueOf(integer)))
                .toList()
                .iterator();
    }

    public Iterator<String> iteratorParallel() {
        return IntStream.range(0, 100000)
                .mapToObj(integer -> getColonAndLineBreak(String.valueOf(integer)))
                .parallel()
                .toList()
                .iterator();
    }


    private String getColonAndLineBreak(String num, String threadName) {
        return num + COLON + threadName + LINE_BREAK;
    }

    private String getColonAndLineBreak(String num) {
        return num + LINE_BREAK;
    }
}

