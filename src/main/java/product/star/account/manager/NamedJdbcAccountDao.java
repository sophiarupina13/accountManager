package product.star.account.manager;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class NamedJdbcAccountDao implements AccountDao {

    private static final String DELETE_ALL_ACCOUNTS_SQL = "" +
            "DELETE " +
            "FROM ACCOUNT";

    private static final String GET_ACCOUNT_SQL = "" +
            "SELECT" +
            "   ID," +
            "   AMOUNT " +
            "FROM ACCOUNT " +
            "WHERE ID = :id";

    private static final String SET_AMOUNT_SQL = "" +
            "UPDATE ACCOUNT " +
            "SET AMOUNT = :amount " +
            "WHERE ID = :id";

    private static final String CREATE_ACCOUNT_WITHOUT_ID_SQL = "" +
            "INSERT INTO ACCOUNT(AMOUNT)" +
            "VALUES(:amount)";

    private static final String CREATE_ACCOUNT_SQL = "" +
            "INSERT INTO ACCOUNT(ID, AMOUNT)" +
            "VALUES(:id, :amount)";

    private static final String GET_ACCOUNTS_SQL = "" +
            "SELECT" +
            "   ID," +
            "   AMOUNT " +
            "FROM ACCOUNT " +
            "WHERE ID IN (:ids)";

    private static final String GET_ALL_ACCOUNTS_SQL = "" +
            "SELECT" +
            "   ID," +
            "   AMOUNT " +
            "FROM ACCOUNT";

    private final String STATS_SQL = "" +
            "SELECT" +
            "   COUNT(ID) as ID_COUNT, " +
            "   SUM(AMOUNT) as TOTAL_SUM " +
            "FROM ACCOUNT";

    private final String LOCK_ACCOUNT_SQL = "" +
            "SELECT * " +
            "FROM ACCOUNT " +
            "WHERE ID = :id FOR UPDATE";

    private static final RowMapper<Account> ACCOUNT_ROW_MAPPER =
            (rs, i) -> new Account(rs.getLong("ID"), rs.getLong("AMOUNT"));

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NamedJdbcAccountDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void deleteAll() {
        namedParameterJdbcTemplate.update(
                DELETE_ALL_ACCOUNTS_SQL,
                EmptySqlParameterSource.INSTANCE
        );
    }

    @Override
    public Account addAccount(long amount) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                CREATE_ACCOUNT_WITHOUT_ID_SQL,
                new MapSqlParameterSource("amount", amount),
                keyHolder,
                new String[] { "id" }
        );

        var accountId = keyHolder.getKey().longValue();
        return new Account(accountId, amount);
    }

    @Override
    public Account addAccount(long accountId, long amount) {
        namedParameterJdbcTemplate.update(
                CREATE_ACCOUNT_SQL,
                new MapSqlParameterSource()
                        .addValue("id", accountId)
                        .addValue("amount", amount)
        );

        return new Account(accountId, amount);
    }

    @Override
    public List<Account> addAccounts(List<Account> accounts) {
        var args = accounts.stream()
                .map(account -> new MapSqlParameterSource()
                        .addValue("id", account.getId())
                        .addValue("amount", account.getAmount()))
                .toArray(MapSqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(
                CREATE_ACCOUNT_SQL,
                args
        );

        return accounts;
    }

    @Override
    public Account getAccount(long accountId) {
        return namedParameterJdbcTemplate.queryForObject(
                GET_ACCOUNT_SQL,
                new MapSqlParameterSource()
                        .addValue("id", accountId),
                ACCOUNT_ROW_MAPPER
        );
    }

    @Override
    public List<Account> getAccounts(Collection<Long> accountIds) {
        return namedParameterJdbcTemplate.query(
                GET_ACCOUNTS_SQL,
                new MapSqlParameterSource("ids", accountIds),
                ACCOUNT_ROW_MAPPER
        );
    }

    @Override
    public void setAmount(long accountId, long amount) {
        namedParameterJdbcTemplate.update(
                SET_AMOUNT_SQL,
                new MapSqlParameterSource()
                        .addValue("id", accountId)
                        .addValue("amount", amount)
        );
    }

    @Override
    public void updateAccounts(Collection<Account> accounts) {
        var args = accounts.stream()
                .map(account -> new MapSqlParameterSource()
                        .addValue("id", account.getId())
                        .addValue("amount", account.getAmount()))
                .toArray(MapSqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(
                SET_AMOUNT_SQL,
                args
        );
    }

    @Override
    public List<Account> getAllAccounts() {
        return namedParameterJdbcTemplate.query(
                GET_ALL_ACCOUNTS_SQL,
                ACCOUNT_ROW_MAPPER
        );
    }

    @Override
    public AccountStats getTotal() {
        return namedParameterJdbcTemplate.queryForObject(
                STATS_SQL,
                EmptySqlParameterSource.INSTANCE,
                (rs, i) -> new AccountStats(rs.getLong("ID_COUNT"), rs.getLong("TOTAL_SUM"))
        );
    }

    @Override
    public Account lockAccount(long accountId) {
        return namedParameterJdbcTemplate.queryForObject(
                LOCK_ACCOUNT_SQL,
                new MapSqlParameterSource("id", accountId),
                ACCOUNT_ROW_MAPPER
        );
    }

}
