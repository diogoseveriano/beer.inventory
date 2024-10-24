package controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import records.UnitRequest;
import service.UnitService;

@Path(value = "api/units")
public class UnitController {

    @Inject
    UnitService unitService;

    @POST
    @Path("create")
    public Response createUnit(@RequestBody UnitRequest unit) {
        boolean created = unitService.createUnit(unit);
        if (created)
            return Response.status(Response.Status.CREATED).build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }

    @GET
    public Response getAllUnits() {
        return Response.ok(unitService.findAll()).build();
    }

    @DELETE
    @Path("delete")
    public Response deleteUnit(@QueryParam("id") Integer id) {
        if (id == null || !unitService.exists(id))
            return Response.status(Response.Status.NOT_FOUND).build();

        unitService.deleteUnitById(id);
        return Response.ok().build();
    }

}
