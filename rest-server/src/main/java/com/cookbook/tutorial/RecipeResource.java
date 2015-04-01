package com.cookbook.tutorial;

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
	public Response get(@HeaderParam("token") String token,@QueryParam("query") String query, @QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity("Invalid Token").build();
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
			return Response.status(200).entity(DALProvider.getInstance().searchWithQuery(query,page,pageSize)).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity("No such entity").build();
		}
	}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id,@HeaderParam("token") String token) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity("Invalid Token").build();
		}
		try {
			return Response.status(200).entity(DALProvider.getInstance().get(id)).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity("No such entity").build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Recipe recipe,@HeaderParam("token") String token) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity("Invalid Token").build();
		}
		try {
			return Response.status(201).entity(DALProvider.getInstance().create(recipe)).build();
		} catch (InvalidEntityException e) {
			return Response.status(400).entity(e.getMessage()).build();

		}
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") String id,Recipe recipe,@HeaderParam("token") String token) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity("Invalid Token").build();
		}
		try {
			return Response.status(200).entity(DALProvider.getInstance().update(recipe)).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity("No such entity").build();
		} catch (InvalidEntityException e) {
			return Response.status(400).entity(e.getMessage()).build();

		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id,@HeaderParam("token") String token) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity("Invalid Token").build();
		}
		try {
			DALProvider.getInstance().delete(id);
			return Response.status(200).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity("No such entity").build();
		}
	}

	@GET
	@Path("describe")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@HeaderParam("token") String token,@QueryParam("id") Integer id) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity("Invalid Token").build();
		}
		try {
			Recipe recipe = new Recipe();
			recipe.setId(id);
			return Response.status(200).entity(DALProvider.getInstance().describeEntity(recipe)).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity("No such entity").build();
		} catch (InvalidEntityException e) {
			return Response.status(400).entity("Error").build();
		}
	}
}