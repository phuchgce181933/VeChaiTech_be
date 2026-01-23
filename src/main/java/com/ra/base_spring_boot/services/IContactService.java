package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.model.ContactRequest;

public interface IContactService {
    void submitContact(ContactRequest request);

    boolean isEmailDomainValid(String email);
}
