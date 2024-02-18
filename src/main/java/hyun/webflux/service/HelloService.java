package hyun.webflux.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public Long streamingError(Long seq, Boolean errorFlag, Long count) {
        if (errorFlag && (count / 2) == seq) {
            throw new RuntimeException();
        }
        return seq;
    }
}
