package com.codingsaint.covidhelp.resources;

import com.codingsaint.covidhelp.constants.NeighbourHelpConstants;
import com.codingsaint.covidhelp.domains.Error;
import com.codingsaint.covidhelp.domains.Errors;
import com.codingsaint.covidhelp.domains.NeighbourMessageWrapper;
import com.codingsaint.covidhelp.domains.NeighbourUser;
import com.codingsaint.covidhelp.util.CovidHelpUtils;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/api/admin")
public class AdminResource {

    @GET
    @RolesAllowed("admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String adminResource() {
        return "admin";
    }

    @GET
    @Path("/approve/users")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public NeighbourMessageWrapper getPendingUsers() {
        try {
            List<NeighbourUser> users = NeighbourUser.findByActive(false);
            NeighbourMessageWrapper messageWrapper = new NeighbourMessageWrapper(users, null);
            return messageWrapper;

        } catch (Exception e) {
            e.printStackTrace();
            return CovidHelpUtils.getError(NeighbourHelpConstants.ERROR_FETCH_USER, NeighbourHelpConstants.ERROR_FETCH_USER_MESSAGE);
        }

    }

    @POST
    @Path("/approve/users/email/{emailId}")
    @RolesAllowed("admin")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public NeighbourMessageWrapper approveUser(@PathParam("emailId") String emailId) {
        try {
            NeighbourUser user = NeighbourUser.findByEmail(emailId);
            user.setActive(true);
            user.persist();
            NeighbourMessageWrapper messageWrapper = new NeighbourMessageWrapper("User Approved Successfully", null);
            return messageWrapper;

        } catch (Exception e) {
            e.printStackTrace();
            return CovidHelpUtils.getError(NeighbourHelpConstants.ERROR_APPROVING_USER, NeighbourHelpConstants.ERROR_APPROVING_USER_MESSAGE);
        }

    }

    @POST
    @Path("/reject/users/email/{emailId}")
    @RolesAllowed("admin")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public NeighbourMessageWrapper removeUser(@PathParam("emailId") String emailId) {
        try {
            NeighbourUser user = NeighbourUser.findByEmail(emailId);
            user.setActive(true);
            user.delete();
            NeighbourMessageWrapper messageWrapper = new NeighbourMessageWrapper("User Removed Successfully", null);
            return messageWrapper;

        } catch (Exception e) {
            e.printStackTrace();
            return CovidHelpUtils.getError(NeighbourHelpConstants.ERROR_REMOVING_USER, NeighbourHelpConstants.ERROR_REMOVING_USER_MESSAGE);
        }

    }
}
