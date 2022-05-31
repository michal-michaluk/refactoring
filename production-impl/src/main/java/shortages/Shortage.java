package shortages;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public record Shortage(String productRefNo, Map<LocalDate, Long> shortages) {

    public <T> Stream<T> map(BiFunction<LocalDate, Long, T> fun) {
        return shortages.entrySet().stream().map(e -> fun.apply(e.getKey(), e.getValue()));
    }

    public static Builder builder(String productRefNo) {
        return new Builder(productRefNo);
    }

    public static class Builder {
        private final String productRefNo;
        private final Map<LocalDate, Long> shortages;

        public Builder(String productRefNo) {
            this.productRefNo = productRefNo;
            shortages = new HashMap<>();
        }

        public void add(LocalDate day, long missing) {
            shortages.put(day, Math.abs(missing));
        }

        public Shortage build() {
            return new Shortage(productRefNo, shortages);
        }
    }
}
