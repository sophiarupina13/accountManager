package product.star.account.manager;

import java.util.Collection;
import java.util.List;

public interface AccountDao {
    void deleteAll();
    Account addAccount(long amount);
    Account addAccount(long accountId, long amount);
    List<Account> addAccounts(List<Account> accounts);
    Account getAccount(long accountId);
    List<Account> getAccounts(Collection<Long> accountIds);
    void setAmount(long accountId, long amount);
    void updateAccounts(Collection<Account> accounts);
    List<Account> getAllAccounts();
    AccountStats getTotal();
    Account lockAccount(long accountId);
}
