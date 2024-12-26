package controller;

import enums.Role;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.InventoryService;

@Path(value = "api/stock/{warehouse}")
public class StockController {

    @Inject
    InventoryService inventoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC, Role.ROLE_READ_ONLY})
    public Response getStock(@PathParam("warehouse") String warehouse) {
        return Response.ok(inventoryService.findStockByWarehouse(warehouse)).build();
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC, Role.ROLE_READ_ONLY})
    public Response getStockEntry(@PathParam("warehouse") String warehouse, @PathParam("id") long id) {
        return Response.ok(inventoryService.findInventoryOrStockById(id)).build();
    }
}
