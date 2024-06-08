package com.identity_reconciliation.identity_reconciliation.controller;

import com.identity_reconciliation.identity_reconciliation.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/identify")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public Map<String, Object> identifyContact(@RequestBody Map<String, String> payload) {
        return contactService.getContactDetails(payload.get("email"), payload.get("phoneNumber"));
    }
}
