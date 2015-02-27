package com.cookbook.tutorial;

import com.cookbook.tutorial.service.NoSuchEntityException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/ingredient")
public class IngredientResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIngredients(@HeaderParam("token") String token) {

		try {
			System.out.println(token);
			return Response.status(200).entity(DALProvider.getInstance().searchWithQuery("GET ALL FROM INGREDIENT",0,0)).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity("No such entity").build();
		}
	}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIngredient(@PathParam("id") int id,@HeaderParam("token") String token) {

		try {
			System.out.println(token);
			return Response.status(200).entity(DALProvider.getInstance().get(id)).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity("No such entity").build();
		}
	}
 
}