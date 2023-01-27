package rzepi.dam.users.details;

import lombok.RequiredArgsConstructor;
import rzepi.dam.users.details.dto.GitHubUserDetails;
import rzepi.dam.users.details.dto.UserDetails;
import rzepi.dam.users.details.exception.UserDetailsNotFoundException;

@RequiredArgsConstructor
class UserDetailsServiceImpl implements UserDetailsService {

    private final GetGithubUserDetails getGithubUserDetails;
    private final UserDetailsRequestCounterRepository userDetailsRequestCounterRepository;

    @Override
    public UserDetails getDetails(String login) {
        GitHubUserDetails gitHubUserDetails = getGithubUserDetails.call(login);
        if (gitHubUserDetails == null) {
            throw new UserDetailsNotFoundException(login);
        }
        UserDetails userDetails = toUserDetails(gitHubUserDetails);
        userDetailsRequestCounterRepository.increaseRequestCounter(login);
        return userDetails;
    }

    private UserDetails toUserDetails(GitHubUserDetails gitHubUserDetails) {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(gitHubUserDetails.getId());
        userDetails.setLogin(gitHubUserDetails.getLogin());
        userDetails.setName(gitHubUserDetails.getName());
        userDetails.setType(gitHubUserDetails.getType());
        userDetails.setAvatarUrl(gitHubUserDetails.getAvatarUrl());
        userDetails.setCreatedAt(gitHubUserDetails.getCreatedAt());
        userDetails.setCalculations(getCalculations(gitHubUserDetails.getFollowers(), gitHubUserDetails.getPublicRepos()));
        return userDetails;
    }

    private double getCalculations(int followers, int publicRepos) {
        return 6.0 / followers * (2 + publicRepos);
    }
}
