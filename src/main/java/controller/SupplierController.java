package controller;

import enums.Role;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import records.SupplierRequest;
import service.SupplierService;

@Path(value = "api/suppliers")
public class SupplierController {

    @Inject
    SupplierService service;

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC})
    public Response createSupplier(@RequestBody SupplierRequest supplierRequest) {
        boolean created = service.createSupplier(supplierRequest);
        if (created)
            return Response.status(Response.Status.CREATED).build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC, Role.ROLE_READ_ONLY})
    public Response getAllUnits() {
        return Response.ok(service.findAll()).build();
    }


    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC})
    public Response deleteSupplier(@PathParam("id") Integer id) {
        if (id == null || !service.existsById(id))
            return Response.status(Response.Status.NOT_FOUND).build();

        service.deleteSupplierById(id);
        return Response.ok().build();
    }
}
