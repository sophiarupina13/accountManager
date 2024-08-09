package product.star.account.manager;

import java.util.List;

public interface AccountDao {
    Account addAccount(long amount);
    Account addAccount(long accountId, long amount);
    Account getAccount(long accountId);
    void setAmount(long accountId, long amount);
    List<Account> getAllAccounts();
}
