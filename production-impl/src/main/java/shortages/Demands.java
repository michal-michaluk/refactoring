package shortages;

import enums.DeliverySchema;

import java.time.LocalDate;
import java.util.Map;

public class Demands {
    private final Map<LocalDate, DailyDemand> demandsPerDay;

    public Demands(Map<LocalDate, DailyDemand> demands) {
        demandsPerDay = demands;

    }

    public DailyDemand get(LocalDate day) {
        return demandsPerDay.getOrDefault(day, null);
    }

    public record DailyDemand(DeliverySchema schema, long level) {

        public long calculate(long level, long produced) {
            if (schema() == DeliverySchema.atDayStart) {
                return level - level();
            } else if (schema() == DeliverySchema.tillEndOfDay) {
                return level - level() + produced;
            } else if (schema() == DeliverySchema.every3hours) {
                // TODO WTF ?? we need to rewrite that app :/
                throw new UnsupportedOperationException();
            } else {
                // TODO implement other variants
                throw new UnsupportedOperationException();
            }
        }
    }
}
