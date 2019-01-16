package bill;

public class Bill {

    private int denomination;
    private int count;

    public Bill(int denomination, int count) {
        this.denomination = denomination;
        this.count = count;
    }

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void add(int count) {
        this.count += count;
    }

    public void subtract(int count) {
        this.count -= count;
    }
}
