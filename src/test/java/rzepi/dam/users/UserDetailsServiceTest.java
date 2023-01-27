package rzepi.dam.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserDetailsServiceTest {

    @Value("classpath:user_details_response.json")
    private Resource userDetailsResponse;

    @Value("classpath:github_user_details.json")
    private Resource githubUserDetailsResponse;

    @Value("${github.users.api.url}")
    private String externalApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mvc;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void shouldReturnUserDetailsForGivenLogin() throws Exception {
        String login = "octocat";
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(externalApiUrl + "/users/" + login)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                        .body(githubUserDetailsResponse));

        mvc.perform(get("/users/" + login).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asString(userDetailsResponse)));

        mockServer.verify();
    }

    private static String asString(Resource resource) {
        try (InputStream is = resource.getInputStream()) {
            return StreamUtils.copyToString(is, UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
