USE afims_db;

-- Ensure the demo feedback cycle exists and is ACTIVE.
INSERT INTO feedback_cycles (
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

-- Ensure at least one active cycle is available.
UPDATE feedback_cycles
SET status = 'ACTIVE'
WHERE name = '2026 Semester 1 Faculty Feedback';

-- Ensure the demo faculty account is active.
UPDATE users
SET role = 'FACULTY',
    status = 'ACTIVE'
WHERE email = 'faculty@afims.local';

-- Ensure the feedback questions exist.
INSERT INTO questions (
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

INSERT INTO questions (
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

INSERT INTO questions (
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
