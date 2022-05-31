package shortages;


public interface LevelOnDeliveryCalculation {
    LevelOnDeliveryCalculation atDayStart = (long level, long produced, long demand) -> level - demand;
    LevelOnDeliveryCalculation tillEndOfDay = (long level, long produced, long demand) -> level - demand + produced;
    LevelOnDeliveryCalculation exception = (long level, long produced, long demand) -> {
        throw new UnsupportedOperationException();
    };

    long calculate(long level, long produced, long demand);
}
