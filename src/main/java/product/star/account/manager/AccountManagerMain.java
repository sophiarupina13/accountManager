package product.star.account.manager;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import product.star.account.manager.config.AccountManagerConfig;

public class AccountManagerMain {
    public static void main(String[] args) {

        var applicationContext = new AnnotationConfigApplicationContext(AccountManagerConfig.class);

        var accountDao = applicationContext.getBean(AccountDao.class);

        var account = accountDao.getAccount(1L);

        System.out.println(account);

        accountDao.setAmount(1L, 1000L);
        account = accountDao.getAccount(1L);
        System.out.println(account);

        var newAccount = accountDao.addAccount(4000L);

        System.out.println(newAccount);

        var accounts = accountDao.getAllAccounts();
        System.out.println(accounts);

    }
}
