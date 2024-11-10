package controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import records.LoginRequest;
import service.UserService;

@Path(value = "api/login")
public class LoginController {

    @Inject
    UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@RequestBody LoginRequest loginRequest) {
        return Response.ok(userService.login(loginRequest.email(), loginRequest.password())).build();
    }

    @POST
    @Path("/sample")
    public void createSampleUser() throws Exception {
        userService.createSampleUser();
    }

}
