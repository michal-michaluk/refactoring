package shortages;

import entities.ProductionEntity;

import java.time.LocalDate;
import java.util.*;

public class ProductionOutputs {
    private final String productRefNo;
    private final Map<LocalDate, List<ProductionEntity>> outputs;

    public ProductionOutputs(List<ProductionEntity> productions) {
        String productRefNo = null;
        HashMap<LocalDate, List<ProductionEntity>> outputs = new HashMap<>();
        for (ProductionEntity production : productions) {
            if (!outputs.containsKey(production.getStart().toLocalDate())) {
                outputs.put(production.getStart().toLocalDate(), new ArrayList<>());
            }
            outputs.get(production.getStart().toLocalDate()).add(production);
            productRefNo = production.getForm().getRefNo();
        }
        this.productRefNo = productRefNo;
        this.outputs = Collections.unmodifiableMap(outputs);
    }

    public long getLevel(LocalDate day) {
        return outputs.getOrDefault(day, List.of()).stream()
                .mapToLong(ProductionEntity::getOutput)
                .sum();
    }

    public String getProductRefNo() {
        return productRefNo;
    }
}
