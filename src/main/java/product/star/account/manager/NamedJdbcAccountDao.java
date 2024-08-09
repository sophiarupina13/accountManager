package product.star.account.manager;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class NamedJdbcAccountDao implements AccountDao {

    private static final String GET_ACCOUNT_SQL = "" +
            "SELECT" +
            "   ID, " +
            "   AMOUNT " +
            "FROM ACCOUNT " +
            "WHERE ID = :id";

    private static final String SET_AMOUNT_SQL = "" +
            "UPDATE ACCOUNT " +
            "SET AMOUNT = :amount " +
            "WHERE ID = :id";

    private static final String CREATE_ACCOUNT_SQL = "" +
            "INSERT INTO ACCOUNT(AMOUNT)" +
            "VALUES(:amount)";

    private static final String GET_ALL_ACCOUNTS_SQL = "" +
            "SELECT " +
            "   ID," +
            "   AMOUNT " +
            "FROM ACCOUNT";

    private static final RowMapper<Account> ACCOUNT_ROW_MAPPER =
            (rs, i) -> new Account(rs.getLong("ID"), rs.getLong("AMOUNT"));

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NamedJdbcAccountDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Account addAccount(long amount) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                CREATE_ACCOUNT_SQL,
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
                "INSERT INTO ACCOUNT(ID, AMOUNT) VALUES (:id, :amount)",
                new MapSqlParameterSource()
                        .addValue("id", accountId)
                        .addValue("amount", amount)
        );

        return new Account(accountId, amount);
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
    public void setAmount(long accountId, long amount) {
        namedParameterJdbcTemplate.update(
                SET_AMOUNT_SQL,
                new MapSqlParameterSource()
                        .addValue("id", accountId)
                        .addValue("amount", amount)
        );
    }

    @Override
    public List<Account> getAllAccounts() {
        return namedParameterJdbcTemplate.query(
                GET_ALL_ACCOUNTS_SQL,
                ACCOUNT_ROW_MAPPER
        );
    }

}
