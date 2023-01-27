package rzepi.dam.users.configuration;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import rzepi.dam.users.details.GetGithubUserDetails;
import rzepi.dam.users.details.dto.GitHubUserDetails;

@RequiredArgsConstructor
class ExternalApi implements GetGithubUserDetails {

    private final RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "gitHubService")
    public GitHubUserDetails call(String login) {
        return restTemplate.getForObject("/users/" + login, GitHubUserDetails.class);
    }
}
