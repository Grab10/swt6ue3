package swt6.spring.worklog.dto;

public class Grouping {
    Long amount;
    String entry;

    public Long getAmount() {
        return amount;
    }

    public String getEntry() {
        return entry;
    }

    public Grouping() {
    }

    public Grouping(String entry, Long amount) {
        this.amount = amount;
        this.entry = entry;
    }

    @Override
    public String toString() {
        return "Grouping{" +
                "amount=" + amount +
                ", entry='" + entry + '\'' +
                '}';
    }
}
