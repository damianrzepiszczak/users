package rzepi.dam.users.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import rzepi.dam.users.details.UserDetailsService;
import rzepi.dam.users.details.dto.UserDetails;

@RestController
@RequiredArgsConstructor
class UserDetailsController {

    private final UserDetailsService userDetailsService;

    @GetMapping("/users/{login}")
    public ResponseEntity<UserDetails> userDetails(@PathVariable("login") String login) {
        UserDetails userDetails = userDetailsService.getDetails(login);
        return ResponseEntity.ok(userDetails);
    }
}
