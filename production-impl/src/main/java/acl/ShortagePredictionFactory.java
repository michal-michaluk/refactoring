package acl;

import entities.DemandEntity;
import entities.ProductionEntity;
import external.CurrentStock;
import shortages.Demands;
import shortages.ProductionOutputs;
import shortages.ShortagePrediction;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ShortagePredictionFactory {
    private LocalDate today;
    private int daysAhead;
    private CurrentStock stock;
    private List<ProductionEntity> productions;
    private List<DemandEntity> demandsList;

    public ShortagePredictionFactory(LocalDate today, int daysAhead, CurrentStock stock, List<ProductionEntity> productions, List<DemandEntity> demandsList) {
        this.today = today;
        this.daysAhead = daysAhead;
        this.stock = stock;
        this.productions = productions;
        this.demandsList = demandsList;
    }

    public ShortagePrediction create() {
        List<LocalDate> dates = Stream.iterate(today, date -> date.plusDays(1))
                .limit(daysAhead)
                .collect(toList());

        ProductionOutputs outputs = new ProductionOutputs(productions);
        Demands demands = new Demands(demandsList);

        return new ShortagePrediction(stock, dates, outputs, demands);
    }
}
