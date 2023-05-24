package shortages;

import java.time.LocalDateTime;

public interface ProductionRepository {
    ProductionOutputs findFromTime(String productRefNo, LocalDateTime atStartOfDay);
}
