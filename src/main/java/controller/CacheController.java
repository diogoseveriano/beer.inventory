package controller;

import enums.Role;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import service.CacheService;

@Path("api/cache")
@RolesAllowed({Role.ROLE_ADMIN})
public class CacheController {

    @Inject
    CacheService cacheService;

    @GET
    public Response getCache() {
        return Response.ok(cacheService.getAllCacheKeys()).build();
    }
}
