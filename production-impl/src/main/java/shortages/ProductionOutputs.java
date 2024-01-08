package shortages;

import entities.ProductionEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductionOutputs {

    private final Map<LocalDate, List<ProductionEntity>> outputs;

    public ProductionOutputs(Map<LocalDate, List<ProductionEntity>> outputs) {
        this.outputs = outputs;
    }

    public static ProductionOutputs createOutputs(List<ProductionEntity> productions) {
        Map<LocalDate, List<ProductionEntity>> outputs = new HashMap<>();
        for (ProductionEntity production : productions) {
            if (!outputs.containsKey(production.getStart().toLocalDate())) {
                outputs.put(production.getStart().toLocalDate(), new ArrayList<>());
            }
            outputs.get(production.getStart().toLocalDate()).add(production);
        }
        return new ProductionOutputs(outputs);
    }

    public long sumOutputs(LocalDate day) {
        long level = 0;
        for (ProductionEntity production : outputs.get(day)) {
            level += production.getOutput();
        }
        return level;
    }
}
