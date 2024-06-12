# Identity Reconciliation Service

This project is designed to reconcile customer identities using their email addresses and phone numbers. The service consolidates different orders made by the same customer even if they use different contact information. 

If an incoming request has either of phoneNumber or email common to an existing contact but contains new
information, the service will create a “secondary” Contact row.

## Technologies Used

- Java
- Spring Boot
- MySQL
- Docker
- Render.com (for deployment)
- Aiven (for MySQL database hosting)

## Deployed to Render.com

1. Created a new web service on Render.com.
2. Connected your GitHub repository.
3. Set the build and start commands:
    - **Build Command**: `./mvnw clean install`
    - **Start Command**: `java -jar target/fluxkart-identity-reconciliation-0.0.1-SNAPSHOT.jar`
4. Add environment variables for your Aiven MySQL database.
5. Deploy the application.

## API Documentation

### Identify Contact

- **URL**: `https://identity-reconciliation-m4dx.onrender.com/identify`
- **Method**: `POST`
- **Content-Type**: `application/json`

#### Request Payload

```json
{
  "email": "example@example.com",
  "phoneNumber": "1234567890"
}
```

#### Response
```json
{
  "contact": {
    "primaryContactId": 1,
    "emails": ["example@example.com"],
    "phoneNumbers": ["1234567890"],
    "secondaryContactIds": []
  }
}
```

