package controller;

import enums.Role;
import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claims;
import org.jboss.logging.annotations.Param;
import records.LoginResponse;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;

@Path(value = "api/login")
public class LoginController {

    //FOR TESTS ONLY
    @POST
    public Response login(@QueryParam("email") String email, @QueryParam("password") String password) {
        LoginResponse response = LoginResponse.builder()
                .token(Jwt.issuer("https://inventory.cervejaduna.pt/issuer")
                        .expiresIn(Duration.ofHours(1))
                        .upn("geral@cervejaduna.pt")
                        .groups(new HashSet<>(List.of(Role.ROLE_GENERIC)))
                        .claim(Claims.given_name, "Diogo")
                        .claim(Claims.preferred_username, "dseveriano")
                        .sign())
                .username("XOT")
                .email("teste@bis.pt")
                .build();

        return Response.ok(response).build();
    }

}
