package shortages;

import acl.ShortagePredictionFactory;

public class ShortagePredictionService {
    private ShortagePredictionFactory factory;

    public ShortagePredictionService(ShortagePredictionFactory factory) {
        this.factory = factory;
    }

    public ShortageBuilder findShortages() {
        ShortagePrediction prediction = factory.create();
        return prediction.predict();
    }
}
