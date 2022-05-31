package acl;

import entities.DemandEntity;
import entities.ProductionEntity;
import entities.ShortageEntity;
import external.CurrentStock;
import shortages.ShortageBuilder;
import shortages.ShortagePredictionService;
import tools.ShortageFinder;

import java.time.LocalDate;
import java.util.List;

public class ShortageFinderACL {

    public static final boolean TOGGLE_NEW_MODEL = true;

    private ShortageFinderACL() {
    }

    public static List<ShortageEntity> findShortages(LocalDate today, int daysAhead, CurrentStock stock,
                                                     List<ProductionEntity> productions, List<DemandEntity> demandsList) {

        if (TOGGLE_NEW_MODEL) {
            ShortagePredictionFactory factory = new ShortagePredictionFactory(today, daysAhead, stock, productions, demandsList);
            ShortagePredictionService service = new ShortagePredictionService(factory);

            ShortageBuilder shortages = service.findShortages();

            return shortages.toList();
        } else {
            return ShortageFinder.findShortages(today, daysAhead, stock, productions, demandsList);
        }
    }

}
