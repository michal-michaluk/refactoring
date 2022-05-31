package acl;

import entities.ShortageEntity;
import shortages.Shortage;

import java.time.LocalDate;
import java.util.List;

class ShortagesMapper {
    static List<ShortageEntity> toEntities(Shortage shortages) {
        return shortages.map((date, missing) -> {
                    ShortageEntity entity = new ShortageEntity();
                    entity.setRefNo(shortages.productRefNo());
                    entity.setFound(LocalDate.now());
                    entity.setAtDay(date);
                    entity.setMissing(missing);
                    return entity;
                }
        ).toList();
    }
}
