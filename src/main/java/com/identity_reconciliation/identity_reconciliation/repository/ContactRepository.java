package com.identity_reconciliation.identity_reconciliation.repository;

import com.identity_reconciliation.identity_reconciliation.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByEmailOrPhoneNumber(String email, String phoneNumber);
    List<Contact> findByLinkedId(Long linkedId);
}
