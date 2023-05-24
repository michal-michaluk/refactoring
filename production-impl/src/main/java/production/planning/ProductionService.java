package production.planning;

import dao.ProductionDao;
import entities.ProductionEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductionService {
    ProductionDao dao;

    public Map<LocalDate, Long> getOutputs(String productRefNo, LocalDateTime atStartOfDay) {
        List<ProductionEntity> productions = dao.findFromTime(productRefNo, atStartOfDay);
        return productions.stream()
                .collect(Collectors.groupingBy(
                        production -> production.getStart().toLocalDate(),
                        Collectors.summingLong(ProductionEntity::getOutput))
                );
    }
}
