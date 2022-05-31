package acl;

import enums.DeliverySchema;
import shortages.LevelOnDeliveryCalculation;

import java.util.Map;

public class LevelOnDeliveryStrategyPick {
    public static LevelOnDeliveryCalculation pickStrategyVariant(DeliverySchema schema) {
        Map<DeliverySchema, LevelOnDeliveryCalculation> mapping = Map.of(
                DeliverySchema.atDayStart, LevelOnDeliveryCalculation.atDayStart,
                DeliverySchema.tillEndOfDay, LevelOnDeliveryCalculation.tillEndOfDay,
                DeliverySchema.every3hours, LevelOnDeliveryCalculation.exception
        );

        return mapping.getOrDefault(schema, LevelOnDeliveryCalculation.exception);
    }
}
