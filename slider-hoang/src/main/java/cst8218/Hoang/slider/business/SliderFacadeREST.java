/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 * @author Hoang
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
    
 /**
 * Creates a new Slider entity if the provided entity's ID is null.
 * If an ID is provided and matches an existing Slider, it updates the existing Slider
 * with the new values. Non-null values from the new Slider replace those in the existing one.
 * 
 * @param entity The Slider entity to create or update.
 * @return Response indicating the result of the creation or update.
 *         - 201 Created with the created entity if a new entity is created.
 *         - 200 OK with the updated entity if an existing entity is updated.
 *         - 400 Bad Request if the ID is provided but does not match any existing Slider.
 */  
    @POST
    public Response createSlider(Slider entity) {
        // Checks if the ID is null, creating a new entity if so, otherwise treating as an update.
        if (entity.getId() == null) {
            super.create(entity);
            return Response.status(Response.Status.CREATED).entity(entity).build(); // 201 Created
        } else {
            // If ID is provided, treat it as an update
            Slider existingSlider = super.find(entity.getId());
            if (existingSlider != null) {
                entity.updateNonNullAttributes(existingSlider);
                super.edit(existingSlider);
                return Response.ok(existingSlider).build(); // 200 OK
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Slider with specified ID does not exist").build(); // 400 Bad Request
            }
        }
    }

    
/**
 * Updates an existing Slider identified by the provided ID using new values.
 * Ensures that the ID in the URL matches the ID in the request body.
 * Only non-null fields in the provided entity will overwrite values in the existing Slider.
 * 
 * @param id The ID of the Slider to update.
 * @param entity The updated values for the Slider.
 * @return Response indicating the result of the update.
 *         - 200 OK with the updated entity if successful.
 *         - 400 Bad Request if the ID in the URL does not match the ID in the request body,
 *           or if the ID does not correspond to any existing entity.
 */
    @POST
    @Path("{id}")
    public Response updateSliderById(@PathParam("id") Long id, Slider entity) {
        if (!id.equals(entity.getId())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID in the URL and body do not match").build(); // 400 Bad Request
        }

        Slider existingSlider = super.find(id);
        if (existingSlider == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Slider with specified ID does not exist").build(); // 400 Bad Request
        }

        entity.updateNonNullAttributes(existingSlider);
        super.edit(existingSlider);
        return Response.ok(existingSlider).build(); // 200 OK with updated slider
    }

/**
 * Replaces an existing Slider entity with the provided new entity.
 * Removes the old entity and creates a new one based on the provided values.
 * Ensures that the ID in the URL matches the ID in the request body.
 * 
 * @param id The ID of the Slider to replace.
 * @param entity The new Slider entity to replace the old one.
 * @return Response indicating the result of the replacement.
 *         - 200 OK with the new entity if successful.
 *         - 400 Bad Request if the ID in the URL does not match the ID in the request body,
 *           or if the ID does not correspond to any existing entity.
 */
    @PUT
    @Path("{id}")
    public Response replaceSliderById(@PathParam("id") Long id, Slider entity) {
        if (!id.equals(entity.getId())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID in the URL and body do not match").build(); // 400 Bad Request
        }

        Slider existingSlider = super.find(id);
        if (existingSlider == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Slider with specified ID does not exist").build(); // 400 Bad Request
        }

        super.remove(existingSlider);
        super.create(entity);
        return Response.ok(entity).build(); // 200 OK with the new slider
    }

    @PUT
    public Response putNotAllowed() {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                .entity("PUT operation is not allowed on the root resource").build(); // 405 Method Not Allowed
    }

    
/**
 * Removes an existing Slider entity identified by its ID.
 * 
 * @param id The ID of the Slider to remove.
 * @return Response indicating the result of the removal.
 *         - 204 No Content if the entity is successfully removed.
 *         - 404 Not Found if no entity exists with the provided ID.
 */
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        Slider existingSlider = super.find(id);
        if (existingSlider != null) {
            super.remove(existingSlider);
            return Response.noContent().build(); // 204 No Content
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Slider with specified ID does not exist").build(); // 404 Not Found
        }
    }

/**
 * Retrieves a Slider entity by its ID.
 * 
 * @param id The ID of the Slider to retrieve.
 * @return Response containing the Slider entity.
 *         - 200 OK with the Slider entity if found.
 *         - 404 Not Found if no entity exists with the provided ID.
 */
    @GET
    @Path("{id}")
    public Response find(@PathParam("id") Long id) {
        Slider slider = super.find(id);
        if (slider != null) {
            return Response.ok(slider).build(); // 200 OK
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Slider with specified ID does not exist").build(); // 404 Not Found
        }
    }


/**
 * Retrieves all Slider entities.
 * 
 * @return A list of all Slider entities.
 */
    @GET
    public List<Slider> findAll() {
        return super.findAll(); // Returns all sliders
    }

    @GET
    @Path("{from}/{to}")
    public List<Slider> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to}); // Returns a range of sliders
    }

    
/**
 * Retrieves the total number of Slider entities.
 * 
 * @return Response containing the count as a plain text number.
 *         - 200 OK with the count of sliders.
 */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public Response countREST() {
        int count = super.count();
        return Response.ok(String.valueOf(count)).build(); // 200 OK with the count as text
    }
}

