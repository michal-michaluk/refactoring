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
        return new DailyDemand(demands.get(day));
    }

    public static class DailyDemand {
        private final DemandEntity demand;

        public DailyDemand(DemandEntity demand) {
            this.demand = demand;
        }

        public boolean hasDeliverySchema(DeliverySchema schema) {
            return Util.getDeliverySchema(demand) == schema;
        }

        public long getLevel() {
            return Util.getLevel(demand);
        }

        public long calculateLevelOnDelivery(long stock, long produced) {
            long demand = this.getLevel();
            if (this.hasDeliverySchema(DeliverySchema.atDayStart)) {
                return stock - demand;
            } else if (this.hasDeliverySchema(DeliverySchema.tillEndOfDay)) {
                return stock - demand + produced;
            } else if (this.hasDeliverySchema(DeliverySchema.every3hours)) {
                // TODO WTF ?? we need to rewrite that app :/
                throw new UnsupportedOperationException();
            } else {
                // TODO implement other variants
                throw new UnsupportedOperationException();
            }
        }
    }
}
