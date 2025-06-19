CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT, -- H2ではAUTO_INCREMENTでも一応動作するが、IDENTITYの方が推奨
    full_name VARCHAR(30) NOT NULL,
    furigana VARCHAR(50) NOT NULL,
    nickname VARCHAR(30) UNIQUE,
    email_address VARCHAR(50) NOT NULL UNIQUE,
    live_city VARCHAR(50),
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL, -- ENUMの代わりにVARCHARにしてアプリ側で制御
    remark VARCHAR(100),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS students_courses (
    id INT NOT NULL AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_name VARCHAR(30) NOT NULL,
    start_date DATE NOT NULL,
    scheduled_end_date DATE,
    PRIMARY KEY (id)
);