package com.cookbook.tutorial;

import com.cookbook.tutorial.service.DAOCookBookServiceFactory;
import com.cookbook.tutorial.service.*;
import org.parboiled.common.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/ingredient")
public class IngredientResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@HeaderParam("access_token") String token,@QueryParam("query") String query, @QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity(new ErrorResponse("invalid_client","Invalid Token")).build();
		}
		try {
			if(StringUtils.isEmpty(query)){
				query="GET ALL FROM INGREDIENT";
			}
			if(page==null){
				page = 0;
			}
			if(pageSize==null){
				pageSize = 0;
			}
			return Response.status(200).entity(DAOCookBookServiceFactory.getInstance().searchWithQuery(query,page,pageSize)).build();
		} catch (NoSuchEntityException e) {
            return Response.status(404).entity(new ErrorResponse("not_found", "No such entity")).build();
		} catch (Exception ex){
            return Response.status(500).entity("Unexpected error." + ex.getMessage()).build();
        }
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id,@QueryParam("access_token") String token) {

		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity(new ErrorResponse("invalid_client","Invalid Token")).build();
		}
		try {
			return Response.status(200).entity(DAOCookBookServiceFactory.getInstance().get(id)).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity(new ErrorResponse("not_found", "No such entity")).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Ingredient ingredient,@QueryParam("access_token") String token) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity(new ErrorResponse("invalid_client","Invalid Token")).build();
		}
		try {
			return Response.status(201).entity(DAOCookBookServiceFactory.getInstance().create(ingredient)).build();
		} catch (InvalidEntityException e) {
			return Response.status(400).entity(e.getMessage()).build();

		}
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") String id,Ingredient ingredient,@QueryParam("access_token") String token) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity(new ErrorResponse("invalid_client","Invalid Token")).build();
		}
		try {
			return Response.status(200).entity(DAOCookBookServiceFactory.getInstance().update(ingredient)).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity(new ErrorResponse("not_found", "No such entity")).build();
		} catch (InvalidEntityException e) {
			return Response.status(400).entity(e.getMessage()).build();

		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id,@QueryParam("access_token") String token) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity(new ErrorResponse("invalid_client","Invalid Token")).build();
		}
		try {
			DAOCookBookServiceFactory.getInstance().delete(id);
			return Response.status(200).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity(new ErrorResponse("not_found", "No such entity")).build();
		}
	}

	@GET
	@Path("describe")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@QueryParam("access_token") String token) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity(new ErrorResponse("invalid_client","Invalid Token")).build();
		}
		try {
			return Response.status(200).entity(DAOCookBookServiceFactory.getInstance().describeEntity(new Ingredient())).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity(new ErrorResponse("not_found", "No such entity")).build();
		} catch (InvalidEntityException e) {
			return Response.status(400).entity("Error").build();
		}
	}
}