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
    private static final String SELECT_CONTACTS_BY_ORGANIZATION = "SELECT * FROM contacts WHERE LOWER(organization) = LOWER(?)";
    private static final String SELECT_CONTACT_BY_HASH = "SELECT * FROM contacts WHERE hashed_phone_number = ?;";
    private static final String SELECT_CONTACT_BY_MASKED_DETAILS = "SELECT * FROM contacts WHERE masked_name = ? AND masked_phone_number = ?;";

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
                contacts.add(
                    new Contact(
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
    
     // Search contact by hashed phone number
    public Contact searchByPhoneHash(String phoneHash) {
        Contact contact = null;
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_CONTACT_BY_HASH)) {
            stmt.setString(1, phoneHash);
            ResultSet rs = stmt.executeQuery();
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

    // Search contact by masked name and masked phone number
    public Contact searchByMaskedDetails(String maskedName, String maskedPhone) {
        Contact contact = null;
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_CONTACT_BY_MASKED_DETAILS)) {
            stmt.setString(1, maskedName);
            stmt.setString(2, maskedPhone);
            ResultSet rs = stmt.executeQuery();
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
    
    public List<Contact> searchContacts(String phoneHash, String maskedName, String maskedPhone, String organization) {
    List<Contact> contacts = new ArrayList<>();
    String query = "SELECT * FROM contacts WHERE 1=1";

    List<String> conditions = new ArrayList<>();
    List<String> values = new ArrayList<>();

    if (phoneHash != null && !phoneHash.isEmpty()) {
        conditions.add("hashed_phone_number = ?");
        values.add(phoneHash);
    }

    if (maskedName != null && !maskedName.isEmpty()) {
        conditions.add("masked_name = ?");
        values.add(maskedName);
    }
    
    if(maskedPhone != null && !maskedPhone.isEmpty()){
                conditions.add("masked_phone_number = ?");
        values.add(maskedPhone);
    }

    if (organization != null && !organization.isEmpty()) {
        conditions.add("LOWER(organization) = LOWER(?)");
        values.add(organization);
    }

    if (!conditions.isEmpty()) {
        query += " AND " + String.join(" AND ", conditions);
    }

    try (Connection conn = DbUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        for (int i = 0; i < values.size(); i++) {
            stmt.setString(i + 1, values.get(i));
        }

        ResultSet rs = stmt.executeQuery();
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

}
