package product.star.account.manager;

import org.springframework.stereotype.Service;

@Service
public class InMemoryAccountService implements AccountService {

    private final PhoneToAccountResolver phoneToAccountResolver;
    private final AccountDao accountDao;
    private final BlocklistResolver blocklistResolver;

    public InMemoryAccountService(PhoneToAccountResolver phoneToAccountResolver, AccountDao accountDao, BlocklistResolver blocklistResolver) {
        this.phoneToAccountResolver = phoneToAccountResolver;
        this.accountDao = accountDao;
        this.blocklistResolver = blocklistResolver;
    }

    @Override
    public void transfer(long fromId, long toId, long amount) {
        requireNotBlocked(fromId, toId);
        var accountFrom = accountDao.getAccount(fromId);
        var accountTo = accountDao.getAccount(toId);
        if (accountFrom.getAmount() < amount) {
            throw new IllegalStateException("Not enough money on account: " + accountFrom);
        }
        accountDao.setAmount(fromId, accountFrom.getAmount() - amount);
        accountDao.setAmount(toId, accountTo.getAmount() + amount);
    }

    @Override
    public void transferByPhoneNumber(long fromId, String phoneNumberTo, long amount) {
        requireNotBlocked(fromId);
        var to = phoneToAccountResolver.findAccountByPhoneNumber(phoneNumberTo)
                .orElseThrow(() -> new IllegalArgumentException("Account not found by phone: " + phoneNumberTo));

        transfer(fromId, to.getId(), amount);
    }

    void requireNotBlocked(long... accountIds) {
        for (long accountId : accountIds) {
            if (blocklistResolver.isBlocklisted(accountId)) {
                throw new IllegalStateException("Account is blocked: " + accountId);
            }
        }
    }
}
