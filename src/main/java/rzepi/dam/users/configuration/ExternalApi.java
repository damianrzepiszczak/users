package rzepi.dam.users.configuration;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import rzepi.dam.users.details.GetGithubUserDetails;
import rzepi.dam.users.details.dto.GitHubUserDetails;
import rzepi.dam.users.details.exception.UserDetailsNotFoundException;

@RequiredArgsConstructor
class ExternalApi implements GetGithubUserDetails {

    private final RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "gitHubService")
    public GitHubUserDetails call(String login) {
        GitHubUserDetails response = restTemplate.getForObject("/users/" + login, GitHubUserDetails.class);
        if (response == null) {
            throw new UserDetailsNotFoundException(login);
        }
        return response;
    }
}
