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
        for (DemandEntity demand : demands) {
            demandsPerDay.put(demand.getDay(), demand);
        }
        this.demands = Collections.unmodifiableMap(demandsPerDay);
    }

    public boolean hasDemand(LocalDate day) {
        return demands.containsKey(day);
    }

    public DailyDemand get(LocalDate day) {
        if (demands.containsKey(day)) {
            return new DailyDemand(demands.get(day));
        } else {
            return null;
        }
    }

    public static class DailyDemand {
        private final DemandEntity entity;

        public DailyDemand(DemandEntity entity) {
            this.entity = entity;
        }

        public DeliverySchema getDeliverySchema() {
            return Util.getDeliverySchema(entity);
        }

        public long getLevel() {
            return Util.getLevel(entity);
        }

        public long calculateLevelOnDelivery(long level, long produced) {
            long levelOnDelivery;
            if (getDeliverySchema() == DeliverySchema.atDayStart) {
                levelOnDelivery = level - getLevel();
            } else if (getDeliverySchema() == DeliverySchema.tillEndOfDay) {
                levelOnDelivery = level - getLevel() + produced;
            } else if (getDeliverySchema() == DeliverySchema.every3hours) {
                // TODO WTF ?? we need to rewrite that app :/
                throw new UnsupportedOperationException();
            } else {
                // TODO implement other variants
                throw new UnsupportedOperationException();
            }
            return levelOnDelivery;
        }

    }
}
