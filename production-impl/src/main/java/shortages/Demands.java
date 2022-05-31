package shortages;

import acl.LevelOnDeliveryStrategyPick;
import enums.DeliverySchema;

import java.time.LocalDate;
import java.util.Map;

public class Demands {
    private final Map<LocalDate, DailyDemand> demands;

    public Demands(Map<LocalDate, DailyDemand> demands) {
        this.demands = demands;
    }

    public DailyDemand get(LocalDate day) {
        return demands.getOrDefault(day, DailyDemand.NO_DEMAND);
    }

    public static class DailyDemand {
        public static final DailyDemand NO_DEMAND = new DailyDemand(0, LevelOnDeliveryStrategyPick.pickStrategyVariant(DeliverySchema.tillEndOfDay));
        private final long level;
        private final LevelOnDeliveryCalculation strategy;

        public DailyDemand(long level, LevelOnDeliveryCalculation strategy) {
            this.level = level;
            this.strategy = strategy;
        }

        public long calculateLevelOnDelivery(long level, long produced) {
            return strategy.calculate(level, produced, this.level);
        }

        public long calculateEndOfDayLevel(long level, long produced) {
            return level + produced - this.level;
        }
    }
}
