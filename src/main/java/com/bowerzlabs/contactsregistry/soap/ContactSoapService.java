/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.bowerzlabs.contactsregistry.soap;

import com.bowerzlabs.contactsregistry.bean.Contact;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.List;

/**
 *
 * @author bronyst
 */
@WebService
public interface ContactSoapService {

    @WebMethod
    List<Contact> getContactsByOrganization(String organization);
    
        @WebMethod
    List<Contact> getContactByPhoneHash(String phoneHash);

        @WebMethod
    List<Contact> getContactByMaskedDetails(String maskedName, String maskedPhone);
}
