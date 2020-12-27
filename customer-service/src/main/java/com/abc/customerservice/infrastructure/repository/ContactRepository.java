package com.abc.customerservice.infrastructure.repository;

import com.abc.customerservice.domain.entity.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, String> {
}
