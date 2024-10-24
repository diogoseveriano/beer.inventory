package controller;

import enums.Role;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.logging.annotations.Param;
import records.SupplierRequest;
import service.SupplierService;

@Path(value = "api/supplier")
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
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC, Role.ROLE_READ_ONLY})
    public Response getAllUnits() {
        return Response.ok(service.findAll()).build();
    }

    /*
    TODO: re-check the delete methods
    @DELETE
    @Path("delete/{id}")
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC})
    public Response deleteSupplier(@PathParam("id") Integer id) {
        if (id == null || !service.exists(id))
            return Response.status(Response.Status.NOT_FOUND).build();

        service.deleteSupplierById(id);
        return Response.ok().build();
    }

    @DELETE
    @Path("delete/{code}")
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC})
    public Response deleteUnit(@PathParam("code") String code) {
        if (code == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        service.deleteSupplierByCode(code);
        return Response.ok().build();
    }*/

}
