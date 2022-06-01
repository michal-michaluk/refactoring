package acl;

import entities.ShortageEntity;
import shortages.Shortage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public static Shortage fromEntities(List<ShortageEntity> entities) {
        String productRefNo = entities.stream().map(ShortageEntity::getRefNo).findFirst().orElse(null);
        return new Shortage(
                productRefNo,
                entities.stream()
                        .collect(Collectors.toUnmodifiableMap(
                                ShortageEntity::getAtDay,
                                ShortageEntity::getMissing
                        )));
    }
}
