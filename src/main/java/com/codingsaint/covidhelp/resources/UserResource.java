package com.codingsaint.covidhelp.resources;

import com.codingsaint.covidhelp.constants.NeighbourHelpConstants;
import com.codingsaint.covidhelp.domains.NeighbourMessageWrapper;
import com.codingsaint.covidhelp.domains.NeighbourNeeds;
import com.codingsaint.covidhelp.domains.NeighbourUser;
import com.codingsaint.covidhelp.util.CovidHelpUtils;
import io.quarkus.panache.common.Sort;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Path("/api/users")
public class UserResource {
    @ConfigProperty(name = "app.zoneId",defaultValue="Asia/Kolkata")
    String zoneId;
    @GET
    @RolesAllowed({"user","admin"})
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    public String me(@Context SecurityContext securityContext) {
        return securityContext.getUserPrincipal().getName();
    }

    @POST
    @RolesAllowed({"user","admin"})
    @Path("/need")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public NeighbourMessageWrapper neighbourNeeds(@Context SecurityContext securityContext, @Valid  List<NeighbourNeeds> neighbourNeeds) {
        try {
            // user.setId(UUID.randomUUID().toString());
            ZoneId zid = ZoneId.of(zoneId);
            NeighbourUser currentUser=NeighbourUser.findByEmail( securityContext.getUserPrincipal().getName());
            for(NeighbourNeeds need:neighbourNeeds){
                need.setDate(LocalDate.now(zid));
                need.setFlatNumber(currentUser.getFlatNumber());
                need.setName(currentUser.getName());
                need.setMobile(currentUser.getMobile());
                need.setFullfilled(false);
                need.persist(neighbourNeeds);
            }
            NeighbourMessageWrapper neighbourMessageWrapper = new NeighbourMessageWrapper("Your Needs have been updated", null);
            return neighbourMessageWrapper;
        } catch (Exception e) {
            e.printStackTrace();
             return CovidHelpUtils.getError(NeighbourHelpConstants.ERROR_SAVING_NEEDS, NeighbourHelpConstants.ERROR_SAVING_NEEDS_MESSAGE);
        }
    }
    @GET
    @RolesAllowed({"user","admin"})
    @Path("/needs")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public NeighbourMessageWrapper getNeeds(@Context SecurityContext securityContext, @Valid  NeighbourNeeds neighbourNeeds) {
        try {
            // user.setId(UUID.randomUUID().toString());

            Sort sort= Sort.ascending("flatNumber");
            List<NeighbourNeeds> needs=NeighbourNeeds.find("isFullfilled",sort,false).list();

            NeighbourMessageWrapper neighbourMessageWrapper = new NeighbourMessageWrapper(needs, null);
            return neighbourMessageWrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return CovidHelpUtils.getError(NeighbourHelpConstants.ERROR_FETCHING_NEEDS, NeighbourHelpConstants.ERROR_FETCHING_NEEDS_MESSAGE);
        }
    }



    }

