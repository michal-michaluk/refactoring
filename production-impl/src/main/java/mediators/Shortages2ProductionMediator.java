package mediators;

import production.planning.ProductionService;
import shortages.ProductionOutputs;
import shortages.ProductionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class Shortages2ProductionMediator implements ProductionRepository {

    ProductionService service;

    @Override
    public ProductionOutputs findFromTime(String productRefNo, LocalDateTime atStartOfDay) {
        Map<LocalDate, Long> outputs = service.getOutputs(productRefNo, atStartOfDay);
        return new ProductionOutputs(outputs);
    }
}
