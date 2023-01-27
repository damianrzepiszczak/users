package rzepi.dam.users.details.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDetails {
    private Long id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private String createdAt;
    private double calculations;
}
