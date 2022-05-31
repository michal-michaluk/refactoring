package shortages;

import external.CurrentStock;

import java.time.LocalDate;
import java.util.List;

import static shortages.Demands.DailyDemand.NO_DEMAND;

public class ShortagePrediction {
    private CurrentStock stock;
    private List<LocalDate> dates;
    private ProductionOutputs outputs;
    private Demands demands;

    public ShortagePrediction(CurrentStock stock, List<LocalDate> dates, ProductionOutputs outputs, Demands demands) {
        this.stock = stock;
        this.dates = dates;
        this.outputs = outputs;
        this.demands = demands;
    }

    public ShortageBuilder predict() {
        long level = stock.getLevel();

        ShortageBuilder shortages = new ShortageBuilder(outputs.getProductRefNo());
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
        return shortages;
    }
}
