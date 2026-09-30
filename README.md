
## Demo Login Accounts

After running `database/reset_demo_passwords.sql` against `afims_db`:

| Role | Email | Password |
|---|---|---|
| Admin | admin@afims.local | password |
| Faculty | faculty@afims.local | password |
| HOD | hod@afims.local | password |
| Student | student@afims.local | password |

These are development/demo credentials only. Change them before any real deployment.

# AFIMS — Academic Feedback and Improvement Management System

**Unleash Better Learning.**

AFIMS is a professional Spring Boot + Thymeleaf academic feedback platform for students, faculty, HODs and administrators. It uses an original gamma-green / purple visual identity rather than copyrighted superhero artwork.

## Features
- Spring Security authentication and role protection
- Student feedback with active-cycle and duplicate-submission rules
- Configurable database-backed question bank
- Faculty feedback review and local rule-based Gamma Insight Assistant
- Improvement plans with HOD review states
- Admin user approval, feedback-cycle creation, question management and audit log
- MySQL normalized schema with indexes and foreign keys
- Responsive, accessible dark academic SaaS UI
- JUnit / Mockito / Spring Boot test foundation

## Requirements
- Java 17+ (Java 21 is also supported)
- Maven 3.9+
- MySQL 8+
- VS Code + Extension Pack for Java (recommended)

## 1. Database setup on Windows
1. Start MySQL.
2. Open MySQL Workbench or the MySQL client.
3. Run `database/schema.sql`.
4. Run `database/seed.sql`.
5. If the Give Feedback page still has no active cycle, run `database/fix_feedback_workflow.sql`.

The scripts create `afims_db`, its tables, indexes, constraints and clearly labelled demo data.

## 2. Configure the application
Copy `.env.example` to `.env` and set your MySQL password. The application also accepts environment variables directly.

```text
DB_URL=jdbc:mysql://localhost:3306/afims_db?useSSL=false&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=YOUR_PASSWORD
```

For a normal Windows terminal, set them before running Maven, or configure them in your VS Code Java/Spring Boot launch environment. Never commit `.env`.

## 3. Run
Open the `AFIMS` folder in VS Code, then use: 

```bash
mvn clean test
mvn spring-boot:run
```

Open **http://localhost:8080**.

> This build was authored for Maven/Spring Boot. If Maven is not installed, install Maven 3.9+ and reopen the terminal.

## Demo accounts
All demo accounts use the development password **Afims@123**. They are for local development only.

| Role | Email | Password |
|---|---|---|
| Admin | admin@afims.local | Afims@123 |
| Faculty | faculty@afims.local | Afims@123 |
| HOD | hod@afims.local | Afims@123 |
| Student | student@afims.local | Afims@123 |

## Main workflow
**Student:** login → dashboard → submit feedback → view history.

**Faculty:** login → dashboard → review feedback → Gamma Insights → create improvement plan → submit to HOD.

**HOD:** login → review plans → approve / request changes / reject.

**Admin:** login → approve/suspend users → configure questions → create feedback cycles → inspect audit log.

## Project structure
```text
AFIMS/
├── pom.xml
├── README.md
├── .env.example
├── database/
│   ├── schema.sql
│   └── seed.sql
└── src/
    ├── main/java/com/afims/
    │   ├── config/
    │   ├── controller/
    │   ├── dto/
    │   ├── entity/
    │   ├── repository/
    │   ├── security/
    │   └── service/
    ├── main/resources/
    │   ├── templates/
    │   └── static/
    └── test/java/com/afims/
```

## Design system
The UI uses charcoal surfaces, gamma greens, restrained lime accents, royal purple highlights, angular borders and geometric energy details. It intentionally avoids official Marvel/Hulk artwork, logos or fonts.

## Security notes
- Passwords are stored as plain text in this academic/demo build. Do not use this password-storage mode in production.
- CSRF protection is enabled by Spring Security defaults.
- Role checks protect each dashboard path.
- Server-side validation is used for registration and feedback/plan inputs.
- Production secrets are not committed.

## Future enhancements
- Per-question feedback answer persistence in the student UI
- Department-scoped authorization rules for multi-department institutions
- Richer sentiment and keyword models using an optional local NLP service
- Exportable PDF/CSV reports
- Email notifications / password reset through a real mail provider
- Chart.js dashboards with live aggregated API data
- CI pipeline and containerized deployment

## Screenshots
Add screenshots from your local deployment here after running the application.

## Verification note
The project files and ZIP structure were checked in this environment, including Java/HTML structural checks and ZIP integrity. The environment used for packaging has Java 21 but does not have Maven installed and cannot download Maven/dependencies, so `mvn clean test` and a live MySQL-backed `mvn spring-boot:run` could not be executed here. Run those two commands locally after installing Maven and MySQL; the README contains the exact setup.


## Corrected local setup

1. Create a local `.env` file by copying `.env.example`.
2. Put your own MySQL password in `DB_PASSWORD`.
3. Make sure MySQL is running and the `afims_db` database exists.
4. Run the database scripts in this order:
   - `database/schema.sql`
   - `database/seed.sql`
5. Start the application:
   ```powershell
   mvn clean spring-boot:run
   ```
6. Open:
   `http://localhost:8080/`
7. Login page:
   `http://localhost:8080/login`

### Login flow

- `GET /login` is handled by `HomeController` and displays `templates/auth/login.html`.
- `POST /login` is handled by Spring Security through `loginProcessingUrl("/login")`.
- The login form uses Thymeleaf `th:action` so Spring Security CSRF protection can add the token.
- Successful login goes to `/dashboard`, which redirects to the user's role dashboard.

### Demo accounts

The seed file creates these active demo accounts:

- `admin@afims.local`
- `faculty@afims.local`
- `hod@afims.local`
- `student@afims.local`

Use the password configured/verified for your seed data rather than storing a real password in the project ZIP.
