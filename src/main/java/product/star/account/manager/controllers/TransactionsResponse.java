package product.star.account.manager.controllers;

public class TransactionsResponse {

    private final TransactionsResult transactionsResult;

    public TransactionsResponse(TransactionsResult transactionsResult) {
        this.transactionsResult = transactionsResult;
    }

    public TransactionsResult getTransactionsResult() {
        return transactionsResult;
    }
}
