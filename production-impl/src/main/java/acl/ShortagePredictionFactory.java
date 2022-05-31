package acl;

import entities.DemandEntity;
import entities.ProductionEntity;
import external.CurrentStock;
import shortages.Demands;
import shortages.ProductionOutputs;
import shortages.ShortagePrediction;
import shortages.WarehouseStock;
import tools.Util;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ShortagePredictionFactory {
    private LocalDate today;
    private int daysAhead;
    private CurrentStock stock;
    private List<ProductionEntity> productions;
    private List<DemandEntity> demands;

    public ShortagePredictionFactory(LocalDate today, int daysAhead, CurrentStock stock, List<ProductionEntity> productions, List<DemandEntity> demands) {
        this.today = today;
        this.daysAhead = daysAhead;
        this.stock = stock;
        this.productions = productions;
        this.demands = demands;
    }

    public ShortagePrediction create() {
        List<LocalDate> dates = Stream.iterate(today, date -> date.plusDays(1))
                .limit(daysAhead)
                .collect(toList());

        WarehouseStock stock = createStock();
        ProductionOutputs outputs = createOutputs();
        Demands demands = createDemands();

        return new ShortagePrediction(stock, dates, outputs, demands);
    }

    private WarehouseStock createStock() {
        return new WarehouseStock(this.stock.getLevel(), this.stock.getLocked());
    }

    private Demands createDemands() {
        return new Demands(demands.stream()
                .collect(Collectors.toUnmodifiableMap(
                        DemandEntity::getDay,
                        demand -> new Demands.DailyDemand(
                                Util.getLevel(demand),
                                LevelOnDeliveryStrategyPick.pickStrategyVariant(Util.getDeliverySchema(demand))
                        )
                )));
    }

    private ProductionOutputs createOutputs() {
        return new ProductionOutputs(
                productions.stream()
                        .map(production -> production.getForm().getRefNo())
                        .findFirst()
                        .orElse(null),
                Collections.unmodifiableMap(productions.stream()
                        .collect(Collectors.groupingBy(
                                production -> production.getStart().toLocalDate(),
                                Collectors.summingLong(ProductionEntity::getOutput)
                        ))));
    }
}
