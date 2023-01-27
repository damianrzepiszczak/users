package rzepi.dam.users.details;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class UserDetailsServiceConfiguration {

    @Bean
    public UserDetailsService userDetailsService(GetGithubUserDetails getGithubUserDetails, UserDetailsRequestCounterRepository userDetailsRequestCounterRepository) {
        return new UserDetailsServiceImpl(getGithubUserDetails, userDetailsRequestCounterRepository);
    }

    @Bean
    public UserDetailsRequestCounterRepository userDetailsRepository(JdbcTemplate jdbcTemplate) {
        return new UserDetailsRepository(jdbcTemplate);
    }
}
