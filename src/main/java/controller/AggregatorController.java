package controller;

import enums.Role;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import records.StatisticCard;
import service.AggregatorService;

import java.util.List;

@Path(value = "api/aggregator")
public class AggregatorController {

    @Inject
    private AggregatorService aggregatorService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Role.ROLE_ADMIN, Role.ROLE_GENERIC, Role.ROLE_READ_ONLY})
    public Response getStatisticsCards() {
        return Response.ok(aggregatorService.getStatistics()).build();
    }

}
