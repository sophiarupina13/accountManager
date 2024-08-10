package product.star.account.manager;

public class AccountStats {
    long idCount;
    long totalSum;

    public AccountStats(long idCount, long totalSum) {
        this.idCount = idCount;
        this.totalSum = totalSum;
    }

    @Override
    public String toString() {
        return "AccountStats{" +
                "idCount=" + idCount +
                ", totalSum=" + totalSum +
                '}';
    }
}
