package hyun.webflux.controller;

import hyun.webflux.CommonMethod;
import hyun.webflux.service.HelloService;
import hyun.webflux.vo.Hello;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static hyun.webflux.CommonMethod.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloController {

    private final HelloService helloService;

    @GetMapping("/hello/{name}")
    public Mono<String> hello(@PathVariable String name, ServerHttpRequest req) {
        Class<? extends ServerHttpRequest> aClass = req.getClass();
        System.out.println(req.getClass());
        System.out.println("=======================================");
        Arrays.stream(aClass.getDeclaredFields()).forEach(System.out::println);
        System.out.println("=======================================");
        Arrays.stream(aClass.getDeclaredMethods()).forEach(System.out::println);
        Hello hello = new Hello("Hello", name);
        return Mono.just(hello.getMessage() + " !, " + hello.getName());
    }

    @GetMapping("/flux/test/{count}")
    public Flux<String> count(@PathVariable Long count) {
        return Flux.fromIterable(LongStream.range(0, count).boxed().map(Object::toString).collect(Collectors.toSet()));
    }

    @GetMapping(value = "/stream", produces = "text/event-stream")
    public Flux<String> stream() {
        return Flux
                .interval(Duration.ofMillis(300))
                .map(seq -> "Flux sequence: " + seq);
    }

    @GetMapping(value = "/stream/{count}", produces = "text/event-stream")
    public Flux<String> streamCount(@PathVariable Long count, @RequestParam(required = false) Boolean error) {
        if (error == null) {
            error = false;
        }
        Boolean finalError = error;
        return Flux
                .interval(Duration.ofMillis(500))
                .map(seq -> "Flux sequence: " + helloService.streamingError(seq, finalError, count))
                .take(Duration.ofSeconds(count))
                .onErrorReturn("error");
    }

    @GetMapping("/thread")
    public Mono<String> checkThread() {
        getThread();
        return Mono.fromCallable(helloService::hello)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/thread/first")
    public Mono<String> threadFirst() {
        getThread();
        return Mono.fromCallable(helloService::hello).subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/thread/second")
    public Flux<String> threadSecond() {
        return Flux.fromIterable(helloService::iterator);
    }

    @GetMapping("/thread/third")
    public Flux<String> threadThird() { // 제네릭이 ? 이면 list로 리턴
        return Flux.fromIterable(helloService::iteratorParallel);
    }

//    @GetMapping("/thread/fourth")
//    public Flux<?> threadFourth() {
//        return Flux.fromIterable(helloService::iteratorParallel);
//    }


}
