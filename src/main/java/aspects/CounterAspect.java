package aspects;

import entities.Event;
import entities.StatisticEntry;
import entities.Ticket;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Nikolai_Shuvaev on 1/11/2017
 */
@Aspect
public class CounterAspect {

    private static Map<Long, StatisticEntry> counters = new HashMap<>();

    @Pointcut("execution(* services.EventService.getEventByName(..))")
    public void eventNameAccess() {

    }

    @Pointcut("execution(* services.BookingService.getTotalPrice(..))")
    public void eventPriceAccess() {

    }

    @Pointcut("execution(* services.BookingService.bookTicket(..))")
    public void eventBooking() {

    }

 /*   @Pointcut("execution(* services..*(..))")
    public void printAll() {

    }

    @Before("printAll()")
    public void print(JoinPoint joinPoint){
        System.out.println("Hello - " + joinPoint.getTarget());
    }*/


    @AfterReturning(pointcut = "eventNameAccess()",
                    returning = "event")
    public void countEventByNameAccess(Event event) {
        StatisticEntry statisticEntry = getStatisticEntry(event);
        Long priceQueriedCount = statisticEntry.getAccessedByNameCount();
        statisticEntry.setAccessedByNameCount(++priceQueriedCount);
    }

    @AfterReturning("eventPriceAccess() && args(event, ..)")
    public void countEventPriceAccess(Event event) {
        StatisticEntry statisticEntry = getStatisticEntry(event);
        Long priceQueriedCount = statisticEntry.getPriceQueriedCount();
        statisticEntry.setPriceQueriedCount(++priceQueriedCount);
    }

    @AfterReturning("eventBooking() && args(tickets, ..)")
    public void countEventBooking(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            StatisticEntry statisticEntry = getStatisticEntry(ticket.getEvent());
            Long ticketBookingCount = statisticEntry.getTicketBookingCount();
            statisticEntry.setTicketBookingCount(++ticketBookingCount);
        }
    }


    private StatisticEntry getStatisticEntry(Event event) {
        StatisticEntry statisticEntry = counters.get(event.getId());
        if (statisticEntry == null) {
            statisticEntry = new StatisticEntry();
            counters.put(event.getId(), statisticEntry);
        }
        return statisticEntry;
    }

    public Map<Long, StatisticEntry> getCounters() {
        return counters;
    }
}
