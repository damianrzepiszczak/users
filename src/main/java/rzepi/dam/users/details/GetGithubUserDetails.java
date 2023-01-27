package rzepi.dam.users.details;

import rzepi.dam.users.details.dto.GitHubUserDetails;

public interface GetGithubUserDetails {
    GitHubUserDetails call(String login);
}
