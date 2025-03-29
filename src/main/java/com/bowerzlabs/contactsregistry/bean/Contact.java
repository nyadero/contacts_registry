/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bowerzlabs.contactsregistry.bean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author bronyst
 */
public class Contact {
     private int id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String idNumber;
    private String dateOfBirth;
    private String gender;
    private String organization;
    private String maskedName;
    private String maskedPhoneNumber;
    private String hashedPhoneNumber;

    public Contact(int id, String fullName, String phoneNumber, String email, String idNumber, String dateOfBirth, String gender, String organization) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.organization = organization;
         this.maskedName = generateMaskedName(fullName);
        this.maskedPhoneNumber = generateMaskedPhoneNumber(phoneNumber);
        this.hashedPhoneNumber = hashPhoneNumber(phoneNumber);
    }
    
    

    public Contact(String fullName, String phoneNumber, String email, String idNumber, String dateOfBirth, String gender, String organization) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.organization = organization;
        this.maskedName = generateMaskedName(fullName);
        this.maskedPhoneNumber = generateMaskedPhoneNumber(phoneNumber);
        this.hashedPhoneNumber = hashPhoneNumber(phoneNumber);
    }

    public Contact(int id, String fullName, String phoneNumber, String email, String idNumber, String dateOfBirth, String gender, String organization, String maskedName, String maskedPhoneNumber, String hashedPhoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.organization = organization;
        this.maskedName = maskedName;
        this.maskedPhoneNumber = maskedPhoneNumber;
        this.hashedPhoneNumber = hashedPhoneNumber;
    }
    
    

    public Contact() {
    }

    
    public String generateMaskedName(String fullName) {
        String[] parts = fullName.split(" ");
        if (parts.length > 1) {
            return parts[0] + " " + parts[1].charAt(0) + ".";
        }
        return fullName;
    }

    public String generateMaskedPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() >= 6) {
            return phoneNumber.substring(0, 6) + "***";
        }
        return phoneNumber;
    }

    public String hashPhoneNumber(String phoneNumber) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(phoneNumber.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing phone number", e);
        }
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMaskedName(String maskedName) {
        this.maskedName = maskedName;
    }

    public void setHashedPhoneNumber(String hashedPhoneNumber) {
        this.hashedPhoneNumber = hashedPhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getMaskedName() {
        return maskedName;
    }

    public String getMaskedPhoneNumber() {
        return maskedPhoneNumber;
    }

    public String getHashedPhoneNumber() {
        return hashedPhoneNumber;
    }

    @Override
    public String toString() {
        return "Contact{" + "id=" + id + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber + ", email=" + email + ", idNumber=" + idNumber + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", organization=" + organization + ", maskedName=" + maskedName + ", maskedPhoneNumber=" + maskedPhoneNumber + ", hashedPhoneNumber=" + hashedPhoneNumber + '}';
    }

    public void setMaskedPhoneNumber(String maskedPhoneNumber) {
    }
}
