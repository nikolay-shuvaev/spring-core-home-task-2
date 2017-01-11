package aspects;

import entities.Event;
import entities.StatisticEntry;
import entities.Ticket;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Nikolai_Shuvaev on 1/11/2017
 */
@Aspect
@Component
public class CounterAspect {

    private Map<Long, StatisticEntry> counters = new HashMap<>();

    @Pointcut("execution(* services.EventService.getEventByName(..))")
    private void eventNameAccess() {

    }

    @Pointcut("execution(* services.BookingService.getTotalPrice(..))")
    private void eventPriceAccess() {

    }

    @Pointcut("execution(* services.BookingService.bookTicket(..))")
    private void eventBooking() {

    }

    @Before("eventNameAccess()")
    public void countEventByNameAccess(JoinPoint joinPoint) {
        Event event = ((Event) joinPoint.getTarget());
        StatisticEntry statisticEntry = getStatisticEntry(event);
        Long priceQueriedCount = statisticEntry.getAccessedByNameCount();
        statisticEntry.setAccessedByNameCount(++priceQueriedCount);
    }

    @Before("eventPriceAccess() && args(event, ..)")
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
        }
        return statisticEntry;
    }
}
