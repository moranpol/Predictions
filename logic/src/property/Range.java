package property;

import java.io.Serializable;
import java.util.Objects;

public class Range implements Serializable {
    private final Double from;
    private final Double to;

    public Range(Double from, Double to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range range = (Range) o;
        return Objects.equals(from, range.from) && Objects.equals(to, range.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }
}
