package com.codingsaint.covidhelp.resources;


import com.codingsaint.covidhelp.constants.NeighbourHelpConstants;
import com.codingsaint.covidhelp.domains.Error;
import com.codingsaint.covidhelp.domains.Errors;
import com.codingsaint.covidhelp.domains.NeighbourMessageWrapper;
import com.codingsaint.covidhelp.domains.NeighbourUser;
import com.codingsaint.covidhelp.util.CovidHelpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/api/public")
public class PublicResource {
    private static  final Logger LOGGER= LoggerFactory.getLogger(PublicResource.class);

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String publicResource() {
        return "public";
    }

    @Path("/register")
    @PermitAll
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public NeighbourMessageWrapper register(final @Valid NeighbourUser user) {
        try {
           // user.setId(UUID.randomUUID().toString());
            user.setActive(false);
            user.persist(user);
            NeighbourMessageWrapper neighbourMessageWrapper = new NeighbourMessageWrapper("User created successfully, Admin need to approve", null);
            return neighbourMessageWrapper;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error while saving user : {}",e.getMessage());
             return CovidHelpUtils.getError(NeighbourHelpConstants.ERROR_AT_SAVE_USER_CODE, NeighbourHelpConstants.ERROR_AT_SAVE_USER_MESSAGE);
        }
    }

    @Path("/register/admin")
    @PermitAll
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public NeighbourMessageWrapper registerAdmin(final @Valid NeighbourUser user) {
        try {
            // user.setId(UUID.randomUUID().toString());
            NeighbourUser userAdmin=user.findByRole("admin");
            if(userAdmin!=null){
                LOGGER.error("Error while creating admin user as admin exists");
               return CovidHelpUtils.getError(NeighbourHelpConstants.ERROR_AT_SAVE_ADMIN_USER_EXISTS, NeighbourHelpConstants.ERROR_AT_SAVE_ADMIN_USER_EXISTS_MESSAGE);
            }
            user.setActive(true);
            user.setRole("admin");
            user.persist(user);
            NeighbourMessageWrapper neighbourMessageWrapper = new NeighbourMessageWrapper("Admin User created successfully", null);
            return neighbourMessageWrapper;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error while saving user : {}",e.getMessage());
            return CovidHelpUtils.getError(NeighbourHelpConstants.ERROR_AT_SAVE_USER_CODE, NeighbourHelpConstants.ERROR_AT_SAVE_USER_MESSAGE);

        }
    }

}
