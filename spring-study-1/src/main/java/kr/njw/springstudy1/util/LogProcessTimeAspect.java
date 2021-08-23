package kr.njw.springstudy1.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogProcessTimeAspect {

    @Around("execution(public * kr.njw.springstudy1.service..*.*(..))" +
            " || execution(public * kr.njw.springstudy1.repository..*.*(..))")
    public Object checkProcessTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            long processTime = System.currentTimeMillis() - startTime;
            System.out.println(joinPoint.toLongString() + " : " + processTime + "ms");
        }
    }
}
