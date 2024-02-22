package hyun.webflux.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class ServiceAop {

    @Around("execution(* hyun.webflux.service.HelloService.iterator*(..))")
    public Object logServiceExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;
        log.info("{} executed in {} ns", joinPoint.getSignature(), executionTime);
        return result;
    }
}
