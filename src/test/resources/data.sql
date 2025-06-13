INSERT INTO students ( full_name, furigana,nickname,email_address,live_city,age,gender,remark,is_deleted)
VALUES
 ('山田 太郎', 'やまだ たろう','ヤマさん','taro@example.com','東京都新宿区',25,'男性','',false),
 ('鈴木 花子', 'すずき はなこ','ハナ','hanako@example.com','大阪府大阪市',30,'女性','',false),
 ('田中 一郎', 'たなか いちろう','イチロー','ichiro@example.com','北海道札幌市',28,'男性','',false),
 ('佐藤 美咲', 'さとう みさき','みさき','misaki@example.com','愛知県名古屋市',22,'女性','',false),
 ('高橋 翔', 'たかはし しょう','しょう','sho@example.com','福岡県福岡市',27,'男性','',false);

INSERT INTO students_courses (student_id,course_name, start_date, scheduled_end_date)
VALUES
 ( 1,'Javaコース', '2025-01-15','2025-07-15'),
 ( 2,'AWSコース', '2025-04-01','2025-07-15'),
 ( 3,'フロントエンドコース', '2025-03-22','2025-07-15'),
 ( 4,'Webマーケティングコース', '2025-02-10','2025-08-10'),
 ( 5,'デザインコース', '2025-01-29','2025-07-29');