package aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NICK on 12.01.2017.
 */
@Aspect
@Component
public class DiscountAspect {
    private Map<Class, Long> totalDiscountApplyCounter = new HashMap<>();


    @Pointcut("execution(* services.strategies..*(..))")
    public void applyDiscount() {

    }

    @Before(" applyDiscount()")
    public void countTotalAppliedStrategies(JoinPoint joinPoint) {
        Class clazz = joinPoint.getTarget().getClass();
        Long count = totalDiscountApplyCounter.get(clazz);
        count = count != null ? count + 1 : 1L;
        totalDiscountApplyCounter.put(clazz, count);
    }

    public Map<Class, Long> getTotalDiscountApplyCounter() {
        return totalDiscountApplyCounter;
    }
}

