package shortages;

import java.time.LocalDate;

import static shortages.Demands.DailyDemand.NO_DEMAND;

public class ShortagePrediction {
    private final WarehouseStock stock;
    private final DateRange dates;
    private final ProductionOutputs outputs;
    private final Demands demands;

    public ShortagePrediction(DateRange dates, WarehouseStock stock, ProductionOutputs outputs, Demands demands) {
        this.stock = stock;
        this.dates = dates;
        this.outputs = outputs;
        this.demands = demands;
    }

    public Shortage predict() {
        long level = stock.level();

        Shortage.Builder shortages = Shortage.builder(outputs.getProductRefNo());
        for (LocalDate day : dates) {
            Demands.DailyDemand demand = demands.get(day);
            if (demand == NO_DEMAND) {
                level += outputs.getLevel(day);
                continue;
            }
            long produced = outputs.getLevel(day);
            long levelOnDelivery = demand.calculateLevelOnDelivery(level, produced);

            if (levelOnDelivery < 0) {
                shortages.add(day, levelOnDelivery);
            }
            long endOfDayLevel = demand.calculateEndOfDayLevel(level, produced);
            level = Math.max(endOfDayLevel, 0);
        }
        return shortages.build();
    }
}
