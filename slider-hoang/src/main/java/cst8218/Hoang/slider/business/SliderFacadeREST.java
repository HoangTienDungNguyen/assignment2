package cst8218.Hoang.slider.business;

import cst8218.Hoang.slider.entity.Slider;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * RESTful service for managing Slider entities.
 * Handles CRUD operations using HTTP methods.
 * Provides endpoints for creating, editing, removing, finding, 
 * and listing sliders.
 * 
 * @return JSON or XML response
 */
@Stateless
@Path("sliders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SliderFacadeREST extends AbstractFacade<Slider> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public SliderFacadeREST() {
        super(Slider.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Handles creation or updating of a Slider entity.
     */
    @POST
    public Response createOrUpdateSlider(Slider entity) {
        if (entity.getId() == null) {
            // Create a new slider
            super.create(entity);
            return Response.status(Response.Status.CREATED).entity(entity).build();
        } else {
            // Update an existing slider
            Slider existingSlider = super.find(entity.getId());
            if (existingSlider != null) {
                entity.updateNonNullAttributes(existingSlider);
                super.edit(existingSlider);
                return Response.ok(existingSlider).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Slider with ID " + entity.getId() + " not found").build();
            }
        }
    }

    /**
     * Explicitly handles updating a slider by ID.
     */
    @PUT
    @Path("{id}")
    public Response updateSliderById(@PathParam("id") Long id, Slider entity) {
        if (!id.equals(entity.getId())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID in the URL and body do not match").build();
        }

        Slider existingSlider = super.find(id);
        if (existingSlider == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Slider with ID " + id + " not found").build();
        }

        entity.updateNonNullAttributes(existingSlider);
        super.edit(existingSlider);
        return Response.ok(existingSlider).build();
    }

    /**
     * Deletes a slider by ID.
     */
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        Slider existingSlider = super.find(id);
        if (existingSlider != null) {
            super.remove(existingSlider);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Slider with ID " + id + " not found").build();
        }
    }

    /**
     * Retrieves a slider by ID.
     */
    @GET
    @Path("{id}")
    public Response find(@PathParam("id") Long id) {
        Slider slider = super.find(id);
        if (slider != null) {
            return Response.ok(slider).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Slider with ID " + id + " not found").build();
        }
    }

    /**
     * Retrieves all sliders.
     */
    @GET
    public Response getAllSliders() {
        List<Slider> sliders = super.findAll();
        return Response.ok(sliders).build();
    }

    /**
     * Returns the count of sliders.
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public Response countREST() {
        int count = super.count();
        return Response.ok(String.valueOf(count)).build();
    }
}
