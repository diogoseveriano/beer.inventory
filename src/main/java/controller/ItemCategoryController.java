package controller;

import enums.Role;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.ItemCategory;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import records.ItemCategoryRecord;
import records.ItemRequest;
import service.ItemCategoryService;
import service.ItemService;

@Path(value = "api/item-categories")
public class ItemCategoryController {

    @Inject
    ItemCategoryService itemCategoryService;

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC})
    public Response createItem(@RequestBody ItemCategoryRecord categoryRecord) {
        boolean created = itemCategoryService.createItemCategory(categoryRecord);
        if (created)
            return Response.status(Response.Status.CREATED).build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC, Role.ROLE_READ_ONLY})
    public Response getAllItems() {
        return Response.ok(itemCategoryService.findAll()).build();
    }

}
