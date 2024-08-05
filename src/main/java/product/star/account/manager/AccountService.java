package product.star.account.manager;

public interface AccountService {

    void transfer(long fromId, long toId, long amount);
    void transferByPhoneNumber(long fromId, String phoneNumberTo, long amount);
}
