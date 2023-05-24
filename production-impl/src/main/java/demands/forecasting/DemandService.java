package demands.forecasting;

import dao.DemandDao;
import entities.DemandEntity;
import enums.DeliverySchema;
import tools.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// Service
public class DemandService {
    //
    DemandDao demandDao;

    public List<DemandDTO> findFrom(String productRefNo, LocalDateTime atStartOfDay) {
        List<DemandEntity> entities = demandDao.findFrom(atStartOfDay, productRefNo);
        return entities.stream()
                .map(entity -> new DemandDTO(
                        entity.getDay(),
                        Util.getDeliverySchema(entity),
                        Util.getLevel(entity)
                )).toList();
    }

    public record DemandDTO(LocalDate date, DeliverySchema schema, long level) {}

}
