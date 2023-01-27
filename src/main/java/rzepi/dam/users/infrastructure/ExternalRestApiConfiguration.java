package rzepi.dam.users.infrastructure;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import rzepi.dam.users.details.GetGithubUserDetails;

import java.time.Duration;

@Configuration
public class ExternalRestApiConfiguration {

    @Value("${github.users.api.url}")
    private String externalApiUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().rootUri(externalApiUrl).build();
    }

    @Bean
    public GetGithubUserDetails getGithubUserDetails(RestTemplate restTemplate) {
        return new ExternalApi(restTemplate);
    }

    @Bean
    public CircuitBreakerConfigCustomizer externalServiceCircuitBreakerPattern() {
        return CircuitBreakerConfigCustomizer.of("gitHubService", builder -> builder.slidingWindowSize(10)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .minimumNumberOfCalls(5)
                .failureRateThreshold(50.0f));
    }
}
