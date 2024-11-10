package service;

import enums.Role;
import exceptions.LoginException;
import io.smallrye.common.constraint.NotNull;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import model.User;
import org.eclipse.microprofile.jwt.Claims;
import records.LoginResponse;
import utils.PasswordUtils;

import java.time.Duration;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    public LoginResponse login(@NotNull String email, @NotNull String password) {
        Optional<User> user = User.find("email", email).firstResultOptional();
        if (user.isPresent()) {
            try {
                boolean comparisonResult = PasswordUtils.verifyPassword(password, user.get().getPassword());
                if (!comparisonResult)
                    throw new LoginException("Wrong data.");
                else
                    return buildResponse(user.get());
            }
            catch (Exception e) {
                throw new LoginException("Wrong data.");
            }
        } else {
            throw new LoginException("User does not exists.");
        }
    }

    private LoginResponse buildResponse(User user) {
        return LoginResponse.builder()
                .token(Jwt.issuer("https://inventory.cervejaduna.pt/issuer")
                        .expiresIn(Duration.ofHours(1))
                        .upn(user.getEmail())
                        .groups(user.getRoles())
                        .claim(Claims.full_name, user.getFirstName() + " " + user.getLastName())
                        .sign())
                .username(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .build();
    }

}
