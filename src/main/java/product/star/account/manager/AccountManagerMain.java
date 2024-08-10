package product.star.account.manager;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.support.TransactionTemplate;
import product.star.account.manager.config.AccountManagerConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AccountManagerMain {
    public static void main(String[] args) {

        var applicationContext = new AnnotationConfigApplicationContext(AccountManagerConfig.class);

        var namedJdbcAccountDao = applicationContext.getBean(NamedJdbcAccountDao.class);
        var transactionTemplate = applicationContext.getBean(TransactionTemplate.class);
        var accountService = applicationContext.getBean(AccountService.class);

//        List<Account> accounts = new ArrayList<>(Arrays.asList(new Account(10, 5000L), new Account(11, 4000L), new Account(12, 500L)));
//
//        namedJdbcAccountDao.addAccounts(accounts);
//
//        Collection<Long> accountIds = new ArrayList<>(Arrays.asList(new Long[] {10L, 11L}));
//
//        System.out.println(namedJdbcAccountDao.getAccounts(accountIds));
//
//        namedJdbcAccountDao.addAccount(13, 1000L);
//        namedJdbcAccountDao.addAccount(14, 2000L);
//
//        accountService.transfer(13, 14, 500L);
//
//        Collection<Account> accountToUpdate = new ArrayList<>(Arrays.asList(new Account[] {new Account(10, 300L), new Account(11, 400L)}));
//
//        namedJdbcAccountDao.updateAccounts(accountToUpdate);
//
//        System.out.println(namedJdbcAccountDao.getAllAccounts());

        System.out.println(namedJdbcAccountDao.getTotal());

    }
}
