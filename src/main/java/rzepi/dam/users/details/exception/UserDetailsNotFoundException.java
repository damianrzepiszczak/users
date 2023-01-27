package rzepi.dam.users.details.exception;

public class UserDetailsNotFoundException extends RuntimeException {
    public UserDetailsNotFoundException(String login) {
        super("User details not found for " + login + " login");
    }
}
