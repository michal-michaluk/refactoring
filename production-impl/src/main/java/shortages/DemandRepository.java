package shortages;

import java.time.LocalDateTime;

public interface DemandRepository {
    Demands findFrom(String productRefNo, LocalDateTime atStartOfDay);
}
