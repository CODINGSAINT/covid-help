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
import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/api/users")
public class UserResource {
    @ConfigProperty(name = "app.zoneId",defaultValue="Asia/Kolkata")
    String zoneId;
    @GET
    @RolesAllowed({"user","admin"})
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    public Principal me(@Context SecurityContext securityContext) {
        return securityContext.getUserPrincipal();
    }

    @POST
    @RolesAllowed({"user","admin"})
    @Path("/needs")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public NeighbourMessageWrapper neighbourNeeds(@Context SecurityContext securityContext, @Valid  List<NeighbourNeeds> neighbourNeeds) {
        try {
            // user.setId(UUID.randomUUID().toString());
             NeighbourUser currentUser=NeighbourUser.findByEmail( securityContext.getUserPrincipal().getName());
            for(NeighbourNeeds need:neighbourNeeds){
                need=addNeedValuesWithCurrentUserDetails(currentUser,need);
                need.persist(neighbourNeeds);
            }
            NeighbourMessageWrapper neighbourMessageWrapper = new NeighbourMessageWrapper("Your Needs have been updated", null);
            return neighbourMessageWrapper;
        } catch (Exception e) {
            e.printStackTrace();
             return CovidHelpUtils.getError(NeighbourHelpConstants.ERROR_SAVING_NEEDS, NeighbourHelpConstants.ERROR_SAVING_NEEDS_MESSAGE);
        }
    }


    @POST
    @RolesAllowed({"user","admin"})
    @Path("/requirements")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public NeighbourMessageWrapper updateRequirements(@Context SecurityContext securityContext, @Valid Map<String,Object> requirementsMap) {
        try {
            // user.setId(UUID.randomUUID().toString());
            String requirements[]=requirementsMap.get("requirements").toString().split(",");
            String action=requirementsMap.get("action").toString();
            NeighbourUser currentUser=NeighbourUser.findByEmail( securityContext.getUserPrincipal().getName());
            NeighbourNeeds need=null;
            //pick|fulfilled
            for(String needId:requirements){
                need=NeighbourNeeds.findByNeedId(needId);
                need=addNeedValuesWithCurrentUserDetails(currentUser,need);
                if(action.equalsIgnoreCase("pick")){
                    if(need.getFullFilledBy()!=null){
                        need.setPickedBy(currentUser.getEmail());
                        need.setPicked(Boolean.TRUE);
                        need.persist();
                    }

                }else if(action.equalsIgnoreCase("fulfilled")){
                    //If user is admin or the one who posted requirements
                    if((!currentUser.getEmail().equalsIgnoreCase(need.getEmail()))||
                            currentUser.getRole().equalsIgnoreCase("admin")){
                        need.setFullFilledBy(currentUser.getEmail());
                        need.setFullfilled(Boolean.TRUE);
                        need.persist();
                    }

                }

            }
            NeighbourMessageWrapper neighbourMessageWrapper = new NeighbourMessageWrapper("Thanks the needs have been updated", null);
            return neighbourMessageWrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return CovidHelpUtils.getError(NeighbourHelpConstants.ERROR_SAVING_NEEDS, NeighbourHelpConstants.ERROR_SAVING_NEEDS_MESSAGE);
        }
    }

    @POST
    @RolesAllowed({"user","admin"})
    @Path("/need")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public NeighbourMessageWrapper singleNeed(@Context SecurityContext securityContext, @Valid  NeighbourNeeds need) {
        try {
            // user.setId(UUID.randomUUID().toString());
            NeighbourUser currentUser=NeighbourUser.findByEmail( securityContext.getUserPrincipal().getName());
            need=addNeedValuesWithCurrentUserDetails(currentUser,need);
            need.persist();
            NeighbourMessageWrapper neighbourMessageWrapper = new NeighbourMessageWrapper("Your Needs have been updated", null);
            return neighbourMessageWrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return CovidHelpUtils.getError(NeighbourHelpConstants.ERROR_SAVING_NEEDS, NeighbourHelpConstants.ERROR_SAVING_NEEDS_MESSAGE);
        }
    }

    private NeighbourNeeds addNeedValuesWithCurrentUserDetails(NeighbourUser currentUser,NeighbourNeeds need ){
        ZoneId zid = ZoneId.of(zoneId);
        need.setNeedId(UUID.randomUUID().toString());
        need.setDate(LocalDate.now(zid));
        need.setFlatNumber(currentUser.getFlatNumber());
        need.setName(currentUser.getName());
        need.setMobile(currentUser.getMobile());
        need.setEmail(currentUser.getEmail());
        need.setFullfilled(false);
        return need;
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


    @GET
    @RolesAllowed({"user","admin"})
    @Path("/needs/me")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public NeighbourMessageWrapper getMyNeeds(@Context SecurityContext securityContext, @Valid  NeighbourNeeds neighbourNeeds) {
        try {
            // user.setId(UUID.randomUUID().toString());
            NeighbourUser currentUser=NeighbourUser.findByEmail( securityContext.getUserPrincipal().getName());

            Sort sort= Sort.ascending("flatNumber");

            System.out.println(currentUser.getEmail());
            List<NeighbourNeeds> needs=NeighbourNeeds.find("email",sort,currentUser.getEmail()).list();

            NeighbourMessageWrapper neighbourMessageWrapper = new NeighbourMessageWrapper(needs, null);
            return neighbourMessageWrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return CovidHelpUtils.getError(NeighbourHelpConstants.ERROR_FETCHING_NEEDS, NeighbourHelpConstants.ERROR_FETCHING_NEEDS_MESSAGE);
        }
    }
    }

