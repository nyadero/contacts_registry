/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bowerzlabs.contactsregistry.soap;

import jakarta.xml.ws.Endpoint;

/**
 *
 * @author bronyst
 */
public class ContactSoapPublisher {
    public static void main(String[] args) {
        String url = "http://localhost:8040/contactsSoapService";
        Endpoint.publish(url, new ContactSoapServiceImpl());
        System.out.println("SOAP Web Service is running at " + url);
    }
}
