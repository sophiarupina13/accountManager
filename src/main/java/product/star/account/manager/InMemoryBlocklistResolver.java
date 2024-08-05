package product.star.account.manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class InMemoryBlocklistResolver implements BlocklistResolver {

    @Value("#{'${blocklisted.accounts}'.split(',')}")
    private final Set<Long> blocklistedAccounts;

    public InMemoryBlocklistResolver(Set<Long> blocklistedAccounts) {
        this.blocklistedAccounts = blocklistedAccounts;
    }

    @Override
    public boolean isBlocklisted(long accountId) {
        return blocklistedAccounts.contains(accountId);
    }
}
