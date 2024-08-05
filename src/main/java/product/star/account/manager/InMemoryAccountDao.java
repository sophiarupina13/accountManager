package product.star.account.manager;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryAccountDao implements AccountDao {

    private long accountId = 1L;
    private final Map<Long, Account> accountIdMap;

    public InMemoryAccountDao() {
        this.accountIdMap = new HashMap<>();
    }

    @Override
    public Account addAccount(long amount) {
        Account account = new Account(accountId++, amount);
        accountIdMap.put(account.getId(), account);
        return account;
    }

    @Override
    public Optional<Account> findAccount(long accountId) {
        return Optional.ofNullable(accountIdMap.get(accountId));
    }

    @Override
    public Account getAccount(long accountId) {
        return findAccount(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
    }

    @Override
    public void setAmount(long accountId, long amount) {
        var account = getAccount(accountId);
        account.setAmount(amount);
    }
}
