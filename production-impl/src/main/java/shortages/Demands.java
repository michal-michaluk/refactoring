package shortages;

import entities.DemandEntity;
import enums.DeliverySchema;
import tools.Util;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demands {
    private final Map<LocalDate, DemandEntity> demands;

    public Demands(List<DemandEntity> demands) {
        Map<LocalDate, DemandEntity> demandsPerDay = new HashMap<>();
        for (DemandEntity demand1 : demands) {
            demandsPerDay.put(demand1.getDay(), demand1);
        }
        this.demands = Collections.unmodifiableMap(demandsPerDay);
    }

    public DailyDemand get(LocalDate day) {
        if (!demands.containsKey(day)) {
            return DailyDemand.noDemand();
        }
        return new DailyDemand(
                Util.getLevel(demands.get(day)),
                LevelOnDeliveryStrategyPick.pickStrategyVariant(Util.getDeliverySchema(demands.get(day)))
        );
    }

    public static class DailyDemand {
        public static final DailyDemand NO_DEMAND = new DailyDemand(0, LevelOnDeliveryStrategyPick.pickStrategyVariant(DeliverySchema.tillEndOfDay));
        private final long level;
        private final LevelOnDeliveryCalculation strategy;

        public static DailyDemand noDemand() {
            return NO_DEMAND;
        }

        public DailyDemand(long level, LevelOnDeliveryCalculation strategy) {
            this.level = level;
            this.strategy = strategy;
        }

        public long getLevel() {
            return level;
        }

        public long calculateLevelOnDelivery(long level, long produced) {
            return strategy.calculate(level, produced, this.level);
        }

        public long calculateEndOfDayLevel(long level, long produced) {
            return level + produced - this.level;
        }
    }
}
