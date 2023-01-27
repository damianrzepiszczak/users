package rzepi.dam.users.details.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitHubUserDetails {
    private Long id;
    private String login;
    private String name;
    private String type;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("public_repos")
    private int publicRepos;
    private int followers;

    public UserDetails toUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(getId());
        userDetails.setLogin(getLogin());
        userDetails.setName(getName());
        userDetails.setType(getType());
        userDetails.setAvatarUrl(getAvatarUrl());
        userDetails.setCreatedAt(getCreatedAt());
        return userDetails;
    }
}
