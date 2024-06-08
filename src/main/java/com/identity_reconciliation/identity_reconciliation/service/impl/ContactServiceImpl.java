package com.identity_reconciliation.identity_reconciliation.service.impl;

import com.identity_reconciliation.identity_reconciliation.model.Contact;
import com.identity_reconciliation.identity_reconciliation.repository.ContactRepository;
import com.identity_reconciliation.identity_reconciliation.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Map<String, Object> getContactDetails(String email, String phoneNumber) {
        Contact primaryContact = identifyContact(email, phoneNumber);
        List<Contact> relatedContacts = getAllRelatedContacts(primaryContact.getId());

        List<String> emails = relatedContacts.stream()
                .map(Contact::getEmail)
                .filter(e -> e != null)
                .distinct()
                .collect(Collectors.toList());

        List<String> phoneNumbers = relatedContacts.stream()
                .map(Contact::getPhoneNumber)
                .filter(p -> p != null)
                .distinct()
                .collect(Collectors.toList());

        List<Long> secondaryContactIds = relatedContacts.stream()
                .filter(c -> c.getLinkPrecedence().equals("secondary"))
                .map(Contact::getId)
                .collect(Collectors.toList());

        Map<String, Object> contactDetails = new HashMap<>();
        contactDetails.put("primaryContactId", primaryContact.getId());
        contactDetails.put("emails", emails);
        contactDetails.put("phoneNumbers", phoneNumbers);
        contactDetails.put("secondaryContactIds", secondaryContactIds);

        Map<String, Object> response = new HashMap<>();
        response.put("contact", contactDetails);

        return response;
    }

    public Contact identifyContact(String email, String phoneNumber) {
        List<Contact> contacts = contactRepository.findByEmailOrPhoneNumber(email, phoneNumber);

        if (contacts.isEmpty()) {
            Contact newContact = new Contact();
            newContact.setEmail(email);
            newContact.setPhoneNumber(phoneNumber);
            newContact.setLinkPrecedence("primary");
            return contactRepository.save(newContact);
        }

        Contact primaryContact = contacts.stream()
                .filter(c -> c.getLinkPrecedence().equals("primary"))
                .findFirst()
                .orElse(null);

        if (primaryContact == null) {
            primaryContact = contacts.get(0);
            primaryContact.setLinkPrecedence("primary");
            primaryContact.setLinkedId(null);
            contactRepository.save(primaryContact);
        }

        Contact finalPrimaryContact = primaryContact;
        List<Contact> secondaryContacts = contacts.stream()
                .filter(c -> !c.getId().equals(finalPrimaryContact.getId()))
                .collect(Collectors.toList());

        boolean newInfo = true;

        for (Contact contact : contacts) {
            if (contact.getEmail().equals(email) && contact.getPhoneNumber().equals(phoneNumber)) {
                newInfo = false;
                break;
            }
        }

        if (newInfo) {
            Contact newSecondaryContact = new Contact();
            newSecondaryContact.setEmail(email);
            newSecondaryContact.setPhoneNumber(phoneNumber);
            newSecondaryContact.setLinkPrecedence("secondary");
            newSecondaryContact.setLinkedId(primaryContact.getId());
            contactRepository.save(newSecondaryContact);
            secondaryContacts.add(newSecondaryContact);
        }

        return primaryContact;
    }

    public List<Contact> getAllRelatedContacts(Long primaryId) {
        List<Contact> contacts = new ArrayList<>();
        contactRepository.findById(primaryId).ifPresent(contacts::add);
        contacts.addAll(contactRepository.findByLinkedId(primaryId));
        return contacts;
    }
}
