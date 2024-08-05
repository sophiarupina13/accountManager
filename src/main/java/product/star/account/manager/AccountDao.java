package product.star.account.manager;

import java.util.Optional;

public interface AccountDao {
    Account addAccount(long accountId);
    Optional<Account> findAccount(long accountId);
    Account getAccount(long accountId);
    void setAmount(long accountId, long amount);
}
