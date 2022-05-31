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
                Util.getDeliverySchema(demands.get(day)),
                Util.getLevel(demands.get(day))
        );
    }

    public static class DailyDemand {
        public static final DailyDemand NO_DEMAND = new DailyDemand(DeliverySchema.tillEndOfDay, 0);
        private final DeliverySchema schema;
        private final long level;

        public static DailyDemand noDemand() {
            return NO_DEMAND;
        }

        public DailyDemand(DeliverySchema schema, long level) {
            this.schema = schema;
            this.level = level;
        }

        public boolean hasDeliverySchema(DeliverySchema schema) {
            return this.schema == schema;
        }

        public long getLevel() {
            return level;
        }

        public long calculateLevelOnDelivery(long stock, long produced) {
            long demand = getLevel();
            if (hasDeliverySchema(DeliverySchema.atDayStart)) {
                return stock - demand;
            } else if (hasDeliverySchema(DeliverySchema.tillEndOfDay)) {
                return stock - demand + produced;
            } else if (hasDeliverySchema(DeliverySchema.every3hours)) {
                // TODO WTF ?? we need to rewrite that app :/
                throw new UnsupportedOperationException();
            } else {
                // TODO implement other variants
                throw new UnsupportedOperationException();
            }
        }
    }
}
