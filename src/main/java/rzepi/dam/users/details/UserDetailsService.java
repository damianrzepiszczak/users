package rzepi.dam.users.details;

import rzepi.dam.users.details.dto.UserDetails;

public interface UserDetailsService {
    UserDetails getDetails(String login);
}
