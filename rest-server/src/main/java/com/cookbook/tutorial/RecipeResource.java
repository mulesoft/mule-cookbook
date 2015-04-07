package com.cookbook.tutorial;

import com.cookbook.tutorial.service.DAOCookBookServiceFactory;
import com.cookbook.tutorial.service.Constants;
import com.cookbook.tutorial.service.Recipe;
import com.cookbook.tutorial.service.InvalidEntityException;
import com.cookbook.tutorial.service.NoSuchEntityException;
import org.parboiled.common.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/recipe")
public class RecipeResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@QueryParam("access_token") String token,@QueryParam("query") String query, @QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity(new ErrorResponse("invalid_client","Invalid Token")).build();
		}
		try {
			if(StringUtils.isEmpty(query)){
				query="GET ALL FROM RECIPE";
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
	public Response create(Recipe recipe,@QueryParam("access_token") String token) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity(new ErrorResponse("invalid_client","Invalid Token")).build();
		}
		try {
			return Response.status(201).entity(DAOCookBookServiceFactory.getInstance().create(recipe)).build();
		} catch (InvalidEntityException e) {
			return Response.status(400).entity(e.getMessage()).build();

		}
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") String id,Recipe recipe,@QueryParam("access_token") String token) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity(new ErrorResponse("invalid_client","Invalid Token")).build();
		}
		try {
			return Response.status(200).entity(DAOCookBookServiceFactory.getInstance().update(recipe)).build();
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
	public Response get(@QueryParam("access_token") String token,@QueryParam("id") Integer id) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity(new ErrorResponse("invalid_client","Invalid Token")).build();
		}
		try {
			Recipe recipe = new Recipe();
			recipe.setId(id);
			return Response.status(200).entity(DAOCookBookServiceFactory.getInstance().describeEntity(recipe)).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity("No such entity").build();
		} catch (InvalidEntityException e) {
			return Response.status(400).entity(new ErrorResponse("not_found", "No such entity")).build();
		}
	}
}