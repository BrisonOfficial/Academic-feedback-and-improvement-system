USE afims_db;

-- Demo password for all four accounts: password
-- Passwords are intentionally stored as plain text in this academic/demo build.
UPDATE users
SET password = 'password',
    status = 'ACTIVE'
WHERE email IN (
    'admin@afims.local',
    'faculty@afims.local',
    'hod@afims.local',
    'student@afims.local'
);

SELECT email, role, status, password
FROM users
WHERE email IN (
    'admin@afims.local',
    'faculty@afims.local',
    'hod@afims.local',
    'student@afims.local'
);
