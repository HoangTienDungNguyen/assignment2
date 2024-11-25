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
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
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

    @POST
    public Response createOrUpdateSlider(Slider entity) {
        if (entity.getId() == null) {
            super.create(entity);
            return Response.status(Response.Status.CREATED).entity(entity).build();
        } else {
            Slider existingSlider = super.find(entity.getId());
            if (existingSlider != null) {
                entity.updateNonNullAttributes(existingSlider);
                super.edit(existingSlider);
                return Response.ok(existingSlider).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Slider with specified ID does not exist").build();
            }
        }
    }

    @POST
    @Path("{id}")
    public Response updateSliderById(@PathParam("id") Long id, Slider entity) {
        if (!id.equals(entity.getId())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID in the URL and body do not match").build();
        }

        Slider existingSlider = super.find(id);
        if (existingSlider == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Slider with specified ID does not exist").build();
        }

        entity.updateNonNullAttributes(existingSlider);
        super.edit(existingSlider);
        return Response.ok(existingSlider).build();
    }

    @PUT
    @Path("{id}")
    public Response replaceSliderById(@PathParam("id") Long id, Slider entity) {
        if (!id.equals(entity.getId())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID in the URL and body do not match").build();
        }

        Slider existingSlider = super.find(id);
        if (existingSlider == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Slider with specified ID does not exist").build();
        }

        super.remove(existingSlider);
        super.create(entity);
        return Response.ok(entity).build();
    }

    @PUT
    public Response putNotAllowed() {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                .entity("PUT operation is not allowed on the root resource").build();
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        Slider existingSlider = super.find(id);
        if (existingSlider != null) {
            super.remove(existingSlider);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Slider with specified ID does not exist").build();
        }
    }

    @GET
    @Path("{id}")
    public Response find(@PathParam("id") Long id) {
        Slider slider = super.find(id);
        if (slider != null) {
            return Response.ok(slider).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Slider with specified ID does not exist").build();
        }
    }

    /**
     * Retrieves all Slider entities.
     * 
     * @return A Response containing the list of all Slider entities.
     */
    @GET
    @Path("all")
    public Response getAllSliders() {
        List<Slider> sliders = super.findAll();
        return Response.ok(sliders).build();
    }

    @GET
    @Path("{from}/{to}")
    public Response findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        List<Slider> sliders = super.findRange(new int[]{from, to});
        return Response.ok(sliders).build();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public Response countREST() {
        int count = super.count();
        return Response.ok(String.valueOf(count)).build();
    }
}
