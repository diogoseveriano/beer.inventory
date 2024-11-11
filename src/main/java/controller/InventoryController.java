package controller;

import enums.Role;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import records.InventoryManualRequest;
import service.InventoryService;

@Path(value = "api/inventory")
public class InventoryController {

    @Inject
    InventoryService inventoryService;

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC})
    public Response createManualEntryOnInventory(@RequestBody InventoryManualRequest request) {
        boolean created = inventoryService.createManualEntryOnInventory(request);
        if (created)
            return Response.status(Response.Status.CREATED).build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }

    @GET
    @Path("{warehouse}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC, Role.ROLE_READ_ONLY})
    public Response getInventory(@PathParam("warehouse") long warehouse) {
        return Response.ok(inventoryService.findInventoryByWarehouse(warehouse)).build();
    }

}
