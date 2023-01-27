package rzepi.dam.users.details;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
class UserDetailsRepository implements UserDetailsRequestCounterRepository {

    private static final String INSERT_NEW_USER_SQL = "insert into USER_REQUEST_COUNT (LOGIN, REQUEST_COUNT) values (?, ?)";
    private static final String FIND_BY_LOGIN = "select count(*) from USER_REQUEST_COUNT where LOGIN=?";
    private static final String UPDATE_COUNTER_SQL = "update USER_REQUEST_COUNT set REQUEST_COUNT = REQUEST_COUNT + 1 where LOGIN =?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void increaseRequestCounter(String login) {
        if (!exists(login)) {
            jdbcTemplate.update(INSERT_NEW_USER_SQL, login, 1);
        } else {
            jdbcTemplate.update(UPDATE_COUNTER_SQL, login);
        }
    }

    private boolean exists(String login) {
        return jdbcTemplate.queryForObject(FIND_BY_LOGIN, Integer.class, login) > 0;
    }

}
