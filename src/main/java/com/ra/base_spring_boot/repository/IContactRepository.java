package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.ContactRequest;
import com.ra.base_spring_boot.model.constants.ContactStatus;
import org.springframework.data.repository.CrudRepository;

public interface IContactRepository extends CrudRepository<ContactRequest, Long> {
    boolean existsByEmailAndStatus(String email, ContactStatus status);
}
