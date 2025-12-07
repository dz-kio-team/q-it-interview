-- JobGroup 샘플 데이터
INSERT INTO job_group (name, created_at) VALUES
('개발', NOW());

-- Company 샘플 데이터
INSERT INTO company (name, aliases) VALUES
('카카오', '["Kakao", "카카오"]');

-- JobPosition 샘플 데이터 (JobGroup 참조)
INSERT INTO job_position (name, job_group_id, created_at) VALUES
('백엔드 개발자', 1, NOW());

-- Member 샘플 데이터
INSERT INTO member (career_years, name, password, email, nickname, email_verified, profile_image_url, last_login_at, created_at, updated_at) VALUES
(3, '홍길동', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'hong@example.com', '길동이', true, null, null, NOW(), NOW());