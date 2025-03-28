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

    // Add a new contact
    @POST
    public Response addContact(Contact contact) {
        Contact contact1 = new Contact(contact.getFullName(), contact.getPhoneNumber(), contact.getEmail(), contact.getIdNumber(), contact.getDateOfBirth(), contact.getGender(),
                contact.getOrganization());
        contactDao.saveContact(contact1);
        return Response.status(Response.Status.CREATED).entity("Contact added successfully").build();
    }

    // Update an existing contact
    @PUT
    @Path("/{id}")
    public Response updateContact(@PathParam("id") int id, Contact contact) {
        contact.setId(id);
        // Recalculate masked and hashed values
        contact.setMaskedName(contact.generateMaskedName(contact.getFullName()));
        contact.setMaskedPhoneNumber(contact.generateMaskedPhoneNumber(contact.getPhoneNumber()));
        contact.setHashedPhoneNumber(contact.hashPhoneNumber(contact.getPhoneNumber()));
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

    // Search contacts using multiple filters
    @GET
    @Path("/search")
    public Response searchContacts(
            @QueryParam("phoneHash") String phoneHash,
            @QueryParam("maskedName") String maskedName,
            @QueryParam("maskedPhone") String maskedPhone,
            @QueryParam("organization") String organization) {

        List<Contact> contacts = contactDao.searchContacts(phoneHash, maskedName, maskedPhone, organization);
        return Response.ok(contacts).build();
    }
}
