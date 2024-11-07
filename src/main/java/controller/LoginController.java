package controller;

import enums.Role;
import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claims;

import java.util.HashSet;
import java.util.List;

@Path(value = "api/login")
public class LoginController {

    //FOR TESTS ONLY
    @GET
    public Response login() {
        return Response.ok(Jwt.issuer("https://inventory.cervejaduna.pt/issuer")
                        .upn("geral@cervejaduna.pt")
                        .groups(new HashSet<>(List.of(Role.ROLE_GENERIC)))
                        .claim(Claims.given_name, "Diogo")
                        .claim(Claims.preferred_username, "dseveriano")
                        .sign()).build();
    }

}
