package shortages;

import entities.ShortageEntity;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ShortageBuilder {
    private final List<ShortageEntity> entities = new LinkedList<>();
    private final String productRefNo;

    public ShortageBuilder(String productRefNo) {
        this.productRefNo = productRefNo;
    }

    public void add(LocalDate day, long levelOnDelivery) {
        ShortageEntity entity = new ShortageEntity();
        entity.setRefNo(productRefNo);
        entity.setFound(LocalDate.now());
        entity.setAtDay(day);
        entity.setMissing(Math.abs(levelOnDelivery));
        entities.add(entity);
    }

    public List<ShortageEntity> toList() {
        return List.copyOf(entities);
    }
}
