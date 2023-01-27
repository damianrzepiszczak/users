package rzepi.dam.users.details;

interface UserDetailsRequestCounterRepository {
    void increaseRequestCounter(String login);
}
