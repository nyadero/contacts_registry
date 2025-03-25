package com.bowerzlabs.contactsregistry.dao;

import com.bowerzlabs.contactsregistry.bean.Contact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for Contact CRUD operations.
 */
public class ContactDao {
    private static final String INSERT_CONTACT_SQL = "INSERT INTO contacts " +
            "(full_name, phone_number, email, id_number, date_of_birth, gender, organization, masked_name, masked_phone_number, hashed_phone_number) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_ALL_CONTACTS = "SELECT * FROM contacts;";
    private static final String SELECT_CONTACT_BY_ID = "SELECT * FROM contacts WHERE id = ?;";
    private static final String DELETE_CONTACT_SQL = "DELETE FROM contacts WHERE id = ?;";
    private static final String UPDATE_CONTACT_SQL = "UPDATE contacts SET full_name=?, phone_number=?, email=?, id_number=?, date_of_birth=?, gender=?, organization=?, masked_name=?, masked_phone_number=?, hashed_phone_number=? WHERE id=?;";
    private static final String SELECT_CONTACTS_BY_ORGANIZATION = "SELECT full_name, masked_phone_number FROM contacts WHERE organization = ?;";

    public ContactDao() {}

    // Save contact
    public void saveContact(Contact contact) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_CONTACT_SQL)) {

            preparedStatement.setString(1, contact.getFullName());
            preparedStatement.setString(2, contact.getPhoneNumber());
            preparedStatement.setString(3, contact.getEmail());
            preparedStatement.setString(4, contact.getIdNumber());
            preparedStatement.setString(5, contact.getDateOfBirth());
            preparedStatement.setString(6, contact.getGender());
            preparedStatement.setString(7, contact.getOrganization());
            preparedStatement.setString(8, contact.getMaskedName());
            preparedStatement.setString(9, contact.getMaskedPhoneNumber());
            preparedStatement.setString(10, contact.getHashedPhoneNumber());

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Get contact by ID
    public Contact getContact(int id) {
        Contact contact = null;
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_CONTACT_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                contact = new Contact(
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("phone_number"),
                    rs.getString("email"),
                    rs.getString("id_number"),
                    rs.getString("date_of_birth"),
                    rs.getString("gender"),
                    rs.getString("organization"),
                    rs.getString("masked_name"),
                    rs.getString("masked_phone_number"),
                    rs.getString("hashed_phone_number")
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return contact;
    }

    // Delete contact
    public boolean deleteContact(int id) {
        boolean contactDeleted = false;
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_CONTACT_SQL)) {

            preparedStatement.setInt(1, id);
            contactDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return contactDeleted;
    }

    // Update contact
    public boolean updateContact(Contact contact) {
        boolean contactUpdated = false;
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_CONTACT_SQL)) {

            preparedStatement.setString(1, contact.getFullName());
            preparedStatement.setString(2, contact.getPhoneNumber());
            preparedStatement.setString(3, contact.getEmail());
            preparedStatement.setString(4, contact.getIdNumber());
            preparedStatement.setString(5, contact.getDateOfBirth());
            preparedStatement.setString(6, contact.getGender());
            preparedStatement.setString(7, contact.getOrganization());
            preparedStatement.setString(8, contact.getMaskedName());
            preparedStatement.setString(9, contact.getMaskedPhoneNumber());
            preparedStatement.setString(10, contact.getHashedPhoneNumber());
            preparedStatement.setInt(11, contact.getId());

            contactUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return contactUpdated;
    }

    // Get all contacts
    public List<Contact> allContacts() {
        List<Contact> contacts = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_CONTACTS)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                contacts.add(new Contact(
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("phone_number"),
                    rs.getString("email"),
                    rs.getString("id_number"),
                    rs.getString("date_of_birth"),
                    rs.getString("gender"),
                    rs.getString("organization"),
                    rs.getString("masked_name"),
                    rs.getString("masked_phone_number"),
                    rs.getString("hashed_phone_number")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    // Get contacts by organization
    public List<Contact> getContactsByOrganization(String organization) {
        List<Contact> contacts = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_CONTACTS_BY_ORGANIZATION)) {

            stmt.setString(1, organization);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setFullName(rs.getString("full_name"));
//                contact.setMaskedPhoneNumber(rs.getString("masked_phone_number"));
                contacts.add(contact);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return contacts;
    }
}
