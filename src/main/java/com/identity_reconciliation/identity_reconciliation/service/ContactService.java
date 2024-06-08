package com.identity_reconciliation.identity_reconciliation.service;

import java.util.Map;

public interface ContactService {
    Map<String, Object> getContactDetails(String email, String phoneNumber);
}
