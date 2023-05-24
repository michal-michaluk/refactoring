package shortages;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class ShortagePredictionRepository {

    private final DemandRepository demands;
    private final ProductionRepository productions;


    public ShortagePrediction invoke(String productRefNo, LocalDate today) {
        List<LocalDate> dates = Stream.iterate(today, date -> date.plusDays(1))
                .limit(daysAhead)
                .toList();

        ProductionOutputs outputs = productions.findFromTime(productRefNo, today.atStartOfDay());
        Demands demandsPerDay = demands.findFrom(productRefNo, today.atStartOfDay());

        return new ShortagePrediction(productRefNo, stock, dates, outputs, demandsPerDay);
    }
}
