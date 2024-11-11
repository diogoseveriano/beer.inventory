package controller;

import enums.Role;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.WarehouseService;

@Path(value = "api/warehouses")
public class WarehouseController {

    @Inject
    private WarehouseService warehouseService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC, Role.ROLE_READ_ONLY})
    public Response list() {
        return Response.ok(warehouseService.getWarehouseList()).build();
    }

    @GET
    @Path("/default")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC, Role.ROLE_READ_ONLY})
    public Response defaultWarehouse() {
        return Response.ok(warehouseService.getDefaultWarehouse()).build();
    }

}
