package com.cookbook.tutorial;

import com.cookbook.tutorial.service.Constants;
import com.cookbook.tutorial.service.InvalidEntityException;
import com.cookbook.tutorial.service.NoSuchEntityException;
import com.cookbook.tutorial.service.Recipe;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Path("/oauth")
public class AuthorizeResource {

    @GET
    @Path("/authorize")
    @Produces(MediaType.APPLICATION_JSON)
    public Response authorize(@QueryParam("client_id") String accountId, @QueryParam("redirect_uri") String redirectTo) {

        if (CLIENT_ID.equals(accountId)) {
            return Response.status(302).location(
                    UriBuilder.fromUri(redirectTo).queryParam("code", new String[] { CODE }).build()).build();
        } else {
            return Response.status(400).entity(
                    UriBuilder.fromUri(redirectTo).queryParam("error", new String[] { "invalid_client" }).build()).build();
        }
    }

    @POST
    @Path("/accessToken")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response token(MultivaluedMap<String, String> params) {
        try {
            if (params.get("code").get(0).equals(CODE) &&
                    params.get("client_id").get(0).equals(CLIENT_ID) &&
                    params.get("client_secret").get(0).equals(CLIENT_SECRET)
                    ) {
                Map<String, Object> map = new HashMap<>();
                map.put("access_token", Constants.DEFAULT_TOKEN);
                map.put("token_type", "Bearer");

                return Response.status(200).entity(map).build();
            } else {
                return Response.status(401).entity(new ErrorResponse("access_denied", "Code, client_id and/or client_secret didn't match the expected value")).build();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return Response.status(400).entity(new ErrorResponse("server_error", ex.getMessage())).build();
        }
    }

    public static final String CLIENT_ID = "ePU9CxyEVFIXF9nMoGH16s1lUGe5JYPPzClnnVBG";

    public static final String CLIENT_SECRET = "YeKAsQCdr264tNmDdvTdJUAh9TQraJqZpwbEoTuz";

    private static final String CODE = "P1Xw4iR7tOHQiyllRUnlTM08LUwZLAWYC811SCsY";
}