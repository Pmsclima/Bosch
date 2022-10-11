package org.acme;

import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Objects;

// Can´t make this imports
@Path("/subscription")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "subscription", description = "Subscription Operations")
@AllArgsConstructor
@Slf4j
public class SubscriptionResource {

    private final SubscriptionService subscriptionService;

    @GET
    @APIResponse(
            responseCode = "200",
            description = "Get All Subscriptions",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.ARRAY, implementation = Subscription.class)
            )
    )
    public Response get() {
        return Response.ok(subscriptionService.findAll()).build();
    }

    @GET
    @Path("/{subscriptionId}")
    @APIResponse(
            responseCode = "200",
            description = "Get Subscription by subscriptionId",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Subscription.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Subscription does not exist for subscriptionId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getById(@Parameter(name = "subscriptionId", required = true) @PathParam("subscriptionId") Integer subscriptionId) {
        return subscriptionService.findById(subscriptionId)
                .map(subscription -> Response.ok(subscription).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @APIResponse(
            responseCode = "201",
            description = "Subscription Created",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Subscription.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid Subscription",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Subscription already exists for subscriptionId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response post(@NotNull @Valid Subscription subscription, @Context UriInfo uriInfo) {
        subscriptionService.save(subscription);
        URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(subscription.getSubscriptionId())).build();
        return Response.created(uri).entity(subscription).build();
    }

    @PUT
    @Path("/{subscriptionId}")
    @APIResponse(
            responseCode = "204",
            description = "Customer updated",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Subscription.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid Subscription",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Subscription object does not have customerId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Path variable subscriptionId does not match Subscription.subscriptionId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "404",
            description = "No Subscription found for subscriptionId provided",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response put(@Parameter(name = "subscriptionId", required = true) @PathParam("subscriptionId") Integer customerId, @NotNull @Valid Subscription subscription) {
        if (!Objects.equals(subscriptionId, subscription.getSubscriptionId())) {
            throw new ServiceException("Path variable subscriptionId does not match Subscription.subscriptionId");
        }
        subscriptionService.update(subscription);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
