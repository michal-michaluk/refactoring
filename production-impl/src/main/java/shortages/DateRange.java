package shortages;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DateRange implements Iterable<LocalDate> {
    private final List<LocalDate> dates;

    public static DateRange from(LocalDate today, int daysAhead) {
        return new DateRange(Stream.iterate(today, date -> date.plusDays(1))
                .limit(daysAhead)
                .collect(toList())
        );
    }

    private DateRange(List<LocalDate> dates) {
        this.dates = dates;
    }

    @Override
    public Iterator<LocalDate> iterator() {
        return dates.iterator();
    }
}
