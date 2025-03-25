/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bowerzlabs.contactsregistry.soap;

import com.bowerzlabs.contactsregistry.bean.Contact;
import com.bowerzlabs.contactsregistry.dao.ContactDao;
import jakarta.jws.WebService;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author bronyst
 */
@WebService(endpointInterface = "com.bowerzlabs.contactsregistry.soap.ContactSoapService")
public class ContactSoapServiceImpl implements ContactSoapService{
     private final ContactDao contactDao = new ContactDao();
    
    @Override
    public List<Contact> getContactsByOrganization(String organization) {
        return contactDao.allContacts().stream()
                .filter(contact -> contact.getOrganization().equalsIgnoreCase(organization))
                .collect(Collectors.toList());
    }

    @Override
    public List<Contact> getContactByPhoneHash(String phoneHash) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Contact> getContactByMaskedDetails(String maskedName, String maskedPhone) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
