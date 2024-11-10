package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import enums.Role;
import exceptions.LoginException;
import io.smallrye.common.constraint.NotNull;
import io.smallrye.jwt.build.Jwt;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import model.User;
import org.eclipse.microprofile.jwt.Claims;
import records.LoginResponse;
import utils.PasswordUtils;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static enums.Role.ROLE_ADMIN;

@ApplicationScoped
public class UserService {

    //SAMPLE USER FOR TESTING
    @Transactional
    public void createSampleUser() throws Exception {
        User.persist(User.builder()
                .firstName("Diogo")
                .lastName("XPTO")
                .roles(ROLE_ADMIN) //split by comma if needed
                .isActive(true)
                .email("xpto@gmail.com")
                .password(PasswordUtils.hashPassword("12345678"))
                .build());
    }

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
