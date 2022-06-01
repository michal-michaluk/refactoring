package acl;

import entities.DemandEntity;
import entities.ProductionEntity;
import entities.ShortageEntity;
import external.CurrentStock;
import shortages.Shortage;
import shortages.ShortagePrediction;
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

        List<ShortageEntity> oldModel = ShortageFinder.findShortages(today, daysAhead, stock, productions, demandsList);

        if (TOGGLE_NEW_MODEL) {
            ShortagePredictionFactory factory = new ShortagePredictionFactory(today, daysAhead, stock, productions, demandsList);
            ShortagePredictionService service = new ShortagePredictionService(factory);

            ShortagePrediction prediction = factory.create();
            Shortage newModel = prediction.predict();
            Shortage oldModelAsShortage = ShortagesMapper.fromEntities(oldModel);

            if (newModel.equals(oldModelAsShortage)) {
                logSuccess();
            } else {
                logIssue(newModel, oldModelAsShortage, prediction);
            }
        }
        return oldModel;
    }

    private static void logSuccess() {
        System.out.println("Shortage Prediction new model equal to old one");
    }

    private static void logIssue(Shortage newModel, Shortage oldModelAsShortage, ShortagePrediction prediction) {
        LogIssue issue = new LogIssue("Shortage Prediction differs", prediction, newModel, oldModelAsShortage);
        System.out.println(issue);
    }

    private record LogIssue(String message, ShortagePrediction prediction, Shortage newResult, Shortage oldResult) {
    }
}
