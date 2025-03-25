package com.bowerzlabs.contactsregistry.resources;

import com.bowerzlabs.contactsregistry.bean.Contact;
import com.bowerzlabs.contactsregistry.dao.ContactDao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author bronyst
 */
@Path("contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactsResource {

    private final ContactDao contactDao = new ContactDao();

    // Get all contacts
    @GET
    public Response getContacts() {
        List<Contact> contacts = contactDao.allContacts();
        return Response.ok(contacts).build();
    }

    // Get contact by ID
    @GET
    @Path("/{id}")
    public Response getContactById(@PathParam("id") int id) {
        Contact contact = contactDao.getContact(id);
        if (contact != null) {
            return Response.ok(contact).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Contact not found").build();
        }
    }

    // Get contacts by organization
    @GET
    @Path("/organization/{org}")
    public Response getContactsByOrganization(@PathParam("org") String organization) {
        List<Contact> contacts = contactDao.getContactsByOrganization(organization);
        return Response.ok(contacts).build();
    }

    // Add a new contact
    @POST
    public Response addContact(Contact contact) {
        contactDao.saveContact(contact);
        return Response.status(Response.Status.CREATED).entity("Contact added successfully").build();
    }

    // Update an existing contact
    @PUT
    @Path("/{id}")
    public Response updateContact(@PathParam("id") int id, Contact contact) {
        contact.setId(id);
        boolean updated = contactDao.updateContact(contact);
        if (updated) {
            return Response.ok("Contact updated successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Contact not found").build();
        }
    }

    // Delete a contact
    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id) {
        boolean deleted = contactDao.deleteContact(id);
        if (deleted) {
            return Response.ok("Contact deleted successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Contact not found").build();
        }
    }
}
