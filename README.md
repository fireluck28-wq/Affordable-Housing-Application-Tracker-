# Affordable-Housing-Tracker-

## Overview

Affordable Housing Application Tracker is a Java-based desktop application developed to support affordable housing application management under SDG 11: Sustainable Cities and Communities.

The system evaluates applicant eligibility based on household income and family size and provides application status updates through a graphical user interface.

---

## Features

- Applicant registration
- Eligibility assessment
- Affordable housing application tracking
- MVC architecture
- Income and family-size validation
- Household income statistics retrieval from data.gov.my
- JSON parsing using Gson
- Input validation and error handling
- Graceful degradation using cached API data

---

## Design Patterns

### Strategy Pattern

Used to evaluate applicant eligibility using:

- IncomeRule
- FamilyMemberRule

### Observer Pattern

Used for application status change notifications through:

- HousingApplication
- StatusListener

---

## MVC Architecture

### Model

- Applicant
- HousingApplication

### View

- MainView

### Controller

- ApplicationController

---

## External API

API Provider:
data.gov.my

Endpoint:

https://api.data.gov.my/data-catalogue/?id=hh_income

The system retrieves:

- Date
- Mean Household Income
- Median Household Income

JSON data is parsed using Gson.

---

## Technologies Used

- Java 21
- Java Swing
- Maven
- Gson
- JUnit 5

---

## Project Structure

com.housingtracker
│
├── api
├── controller
├── model
├── service
├── strategy
├── view
└── Main

---

## How to Run

1. Clone the repository
    git clone <https://github.com/fireluck28-wq/Affordable-Housing-Application-Tracker-.git>
2. Open project in IntelliJ IDEA
3. Reload Maven dependencies from pom.xml.
4. Navigate to "src/main/java/com/housingtracker/Main.java"
5. Run Main.java

---

## Screenshots

### Main User Interface

<img width="732" height="513" alt="Screenshot 2026-11-22 180334" src="https://github.com/user-attachments/assets/5e1a3ce5-2d93-4603-93cc-0df09fa94d70" />

### Application Approval

<img width="732" height="512" alt="Screenshot 2026-11-22 180545" src="https://github.com/user-attachments/assets/be6f0da3-c436-4460-9ee5-a8f8797eca6b" />

### API Statistics Retrieval

<img width="240" height="185" alt="Screenshot 2026-11-27 235246" src="https://github.com/user-attachments/assets/3668aead-6508-4dd4-a4ef-fb9b6c960b38" />

---

## Testing

System integration testing covers:

- GUI Action
- Successful API Call
- Failed API Response
- End-to-End Logic
- Edge Cases

---

## SDG 11 Alignment

This project supports:

SDG 11.1

Access to adequate, safe and affordable housing through structured housing application evaluation and transparent eligibility assessment.

---

## Contributors

Sec02 Group 3

Scenario 4:
Affordable Housing Application Tracker
