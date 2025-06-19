INSERT INTO students ( full_name, furigana, nickname, email_address, live_city, age, gender, remark, is_deleted)
VALUES
　('山田 太郎', 'やまだ たろう','ヤマさん','taro@example.com','東京都新宿区',40,'男性','',false),
　('鈴木 花子', 'すずき はなこ','ハナ','hanako@example.com','大阪府大阪市',30,'女性','転職希望',false),
　('田中 一郎', 'たなか いちろう','イチロー','ichiro@example.com','北海道札幌市',38,'男性','副業希望',false),
　('佐藤 美咲', 'さとう みさき','みさき','misaki@example.com','愛知県名古屋市',22,'女性','',false),
　('高橋 翔', 'たかはし しょう','しょう','sho@example.com','福岡県福岡市',27,'男性','プログラミング未経験',false);

INSERT INTO students_courses (student_id, course_name, start_date, scheduled_end_date)
VALUES
　(1, 'デザインコース', '2023-10-10', '2024-04-10'),
　(2, 'Javaコース', '2025-05-01', '2025-11-01'),
　(3, 'Webマーケティングコース', '2024-01-15', '2024-07-15'),
　(4, 'フロントエンドコース', '2025-07-10', '2026-01-10'),
　(5, 'AWSコース', '2025-09-09', '2026-03-09');

 INSERT INTO course_status (course_id,　application_status)
 VALUES
　( 1,'受講終了'),
　( 2,'受講中'),
　( 3,'受講終了'),
　( 4,'本申込'),
　( 5,'仮申込');