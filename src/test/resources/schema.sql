CREATE TABLE IF NOT EXISTS students (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(30) NOT NULL,
    furigana VARCHAR(50) NOT NULL,
    nickname VARCHAR(30) UNIQUE,
    email_address VARCHAR(50) NOT NULL UNIQUE,
    live_city VARCHAR(50),
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    remark VARCHAR(100),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS students_courses (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_name VARCHAR(30) NOT NULL,
    start_date DATE NOT NULL,
    scheduled_end_date DATE,
    CONSTRAINT unique_student_course UNIQUE (student_id, course_name)
);

CREATE TABLE course_status (
    id INT  NOT NULL AUTO_INCREMENT,
    course_id INT NOT NULL,
    application_status VARCHAR(20) NOT NULL DEFAULT '仮申込',
    PRIMARY KEY (id,course_id)
);