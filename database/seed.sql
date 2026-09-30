USE afims_db;

INSERT INTO institutions(name)
VALUES ('AFIMS Demo University')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO departments(name, code, institution_id)
SELECT
    'Computer Science and Engineering',
    'CSE',
    i.id
FROM institutions i
WHERE i.name = 'AFIMS Demo University'
ON DUPLICATE KEY UPDATE
    name = VALUES(name);

INSERT INTO users(
    full_name,
    email,
    phone,
    password,
    role,
    status,
    institution,
    department,
    institution_id
)
VALUES
(
    'AFIMS Admin',
    'admin@afims.local',
    '9000000001',
    'password',
    'ADMIN',
    'ACTIVE',
    'AFIMS Demo University',
    'CSE',
    'ADMIN-001'
),
(
    'Dr. Maya Raman',
    'faculty@afims.local',
    '9000000002',
    'password',
    'FACULTY',
    'ACTIVE',
    'AFIMS Demo University',
    'CSE',
    'FAC-001'
),
(
    'Prof. Arjun Rao',
    'hod@afims.local',
    '9000000003',
    'password',
    'HOD',
    'ACTIVE',
    'AFIMS Demo University',
    'CSE',
    'HOD-001'
),
(
    'Demo Student',
    'student@afims.local',
    '9000000004',
    'password',
    'STUDENT',
    'ACTIVE',
    'AFIMS Demo University',
    'CSE',
    'STU-001'
)
ON DUPLICATE KEY UPDATE
    full_name = VALUES(full_name),
    phone = VALUES(phone),
    role = VALUES(role),
    status = VALUES(status),
    institution = VALUES(institution),
    department = VALUES(department),
    institution_id = VALUES(institution_id);

INSERT INTO feedback_cycles(
    name,
    academic_year,
    semester,
    start_date,
    end_date,
    status
)
SELECT
    '2026 Semester 1 Faculty Feedback',
    '2026-27',
    1,
    '2026-08-01',
    '2026-12-31',
    'ACTIVE'
WHERE NOT EXISTS (
    SELECT 1
    FROM feedback_cycles
    WHERE name = '2026 Semester 1 Faculty Feedback'
);

INSERT INTO questions(
    question_text,
    category,
    rating_type,
    required_flag,
    active,
    display_order,
    target_role
)
SELECT
    'The faculty explains concepts clearly.',
    'Teaching Quality',
    'RATING_1_5',
    TRUE,
    TRUE,
    1,
    'STUDENT'
WHERE NOT EXISTS (
    SELECT 1 FROM questions
    WHERE question_text = 'The faculty explains concepts clearly.'
);

INSERT INTO questions(
    question_text,
    category,
    rating_type,
    required_flag,
    active,
    display_order,
    target_role
)
SELECT
    'Communication supports student understanding.',
    'Communication',
    'RATING_1_5',
    TRUE,
    TRUE,
    2,
    'STUDENT'
WHERE NOT EXISTS (
    SELECT 1 FROM questions
    WHERE question_text = 'Communication supports student understanding.'
);

INSERT INTO questions(
    question_text,
    category,
    rating_type,
    required_flag,
    active,
    display_order,
    target_role
)
SELECT
    'The subject is connected to practical examples.',
    'Practical Learning',
    'RATING_1_5',
    TRUE,
    TRUE,
    3,
    'STUDENT'
WHERE NOT EXISTS (
    SELECT 1 FROM questions
    WHERE question_text = 'The subject is connected to practical examples.'
);

INSERT INTO questions(
    question_text,
    category,
    rating_type,
    required_flag,
    active,
    display_order,
    target_role
)
SELECT
    'Doubts receive timely clarification.',
    'Student Support',
    'RATING_1_5',
    TRUE,
    TRUE,
    4,
    'STUDENT'
WHERE NOT EXISTS (
    SELECT 1 FROM questions
    WHERE question_text = 'Doubts receive timely clarification.'
);

INSERT INTO questions(
    question_text,
    category,
    rating_type,
    required_flag,
    active,
    display_order,
    target_role
)
SELECT
    'Learning resources support the course outcomes.',
    'Learning Resources',
    'RATING_1_5',
    TRUE,
    TRUE,
    5,
    'STUDENT'
WHERE NOT EXISTS (
    SELECT 1 FROM questions
    WHERE question_text = 'Learning resources support the course outcomes.'
);

INSERT INTO feedback(
    student_id,
    faculty_id,
    course,
    subject,
    cycle_id,
    overall_rating,
    anonymous,
    comment,
    suggestion
)
SELECT
    s.id,
    f.id,
    'Data Science',
    'Unit 3',
    c.id,
    4,
    FALSE,
    'Teaching is clear but more practical examples and doubt sessions would help.',
    'Add a short practical demonstration after each major topic.'
FROM users s
CROSS JOIN users f
CROSS JOIN feedback_cycles c
WHERE s.email = 'student@afims.local'
  AND f.email = 'faculty@afims.local'
  AND c.name = '2026 Semester 1 Faculty Feedback'
  AND NOT EXISTS (
      SELECT 1
      FROM feedback existing_feedback
      WHERE existing_feedback.student_id = s.id
        AND existing_feedback.faculty_id = f.id
        AND existing_feedback.course = 'Data Science'
        AND existing_feedback.cycle_id = c.id
  )
LIMIT 1;

INSERT INTO notifications(
    user_id,
    title,
    message,
    type
)
SELECT
    u.id,
    'Welcome to AFIMS',
    'Your demo account is active. Explore the feedback workspace.',
    'SYSTEM'
FROM users u
WHERE u.email = 'student@afims.local'
  AND NOT EXISTS (
      SELECT 1
      FROM notifications n
      WHERE n.user_id = u.id
        AND n.title = 'Welcome to AFIMS'
  );
