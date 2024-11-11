package controller;

import enums.Role;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.InventoryService;

@Path(value = "api/stock")
public class StockController {

    @Inject
    InventoryService inventoryService;

    @GET
    @Path("{warehouse}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC, Role.ROLE_READ_ONLY})
    public Response getStock(@PathParam("warehouse") long warehouse) {
        return Response.ok(inventoryService.findStockByWarehouse(warehouse)).build();
    }

}
