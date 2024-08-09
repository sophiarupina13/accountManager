package product.star.account.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class AccountManagerMain {
    public static void main(String[] args) {

        var applicationContext = new AnnotationConfigApplicationContext(AccountManagerMain.class);

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
