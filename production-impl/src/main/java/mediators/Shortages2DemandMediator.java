package mediators;

import demands.forecasting.DemandService;
import shortages.DemandRepository;
import shortages.Demands;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Component
public class Shortages2DemandMediator implements DemandRepository {
    // Autowired
    DemandService demands;

    @Override
    public Demands findFrom(String productRefNo, LocalDateTime atStartOfDay) {
        List<DemandService.DemandDTO> read = demands.findFrom(productRefNo, atStartOfDay);
        Map<LocalDate, Demands.DailyDemand> demands = read.stream()
                .collect(Collectors.toMap(
                        DemandService.DemandDTO::date,
                        dto -> new Demands.DailyDemand(
                                dto.schema(),
                                dto.level()
                        )));
        return new Demands(demands);
    }

}
