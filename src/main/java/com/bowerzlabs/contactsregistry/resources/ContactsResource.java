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
    @Path("/organization")
    public Response getContactsByOrganization(@QueryParam("org") String organization) {
        if (organization == null || organization.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Organization parameter is required\"}")
                    .build();
        }

        List<Contact> contacts = contactDao.getContactsByOrganization(organization);
        return Response.ok(contacts).build();
    }

    // Search contact by phone hash
    @GET
    @Path("/search/phone-hash/{hash}")
    public Response getContactByPhoneHash(@PathParam("hash") String phoneHash) {
        Contact contact = contactDao.searchByPhoneHash(phoneHash);
        if (contact != null) {
            return Response.ok(contact).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Contact not found").build();
        }
    }

    // Search by masked name and phone number
    @GET
    @Path("/search/masked")
    public Response searchByMaskedNameAndPhone(@QueryParam("maskedName") String maskedName, @QueryParam("maskedPhone") String maskedPhone) {
        Contact contact = contactDao.searchByMaskedDetails(maskedName, maskedPhone);
        return Response.ok(contact).build();
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
