package com.cookbook.tutorial;

import com.cookbook.tutorial.service.Constants;
import com.cookbook.tutorial.service.InvalidEntityException;
import com.cookbook.tutorial.service.NoSuchEntityException;
import com.cookbook.tutorial.service.Recipe;
import org.parboiled.common.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authorize")
public class AuthorizeResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@HeaderParam("accountId") String accountId,@HeaderParam("password") String password) {

		if("admin".equals(accountId) && "admin".equals(password)){
			return Response.status(200).entity(Constants.DEFAULT_TOKEN).build();
		} else {
			return Response.status(401).entity("No such entity").build();
		}
	}

}