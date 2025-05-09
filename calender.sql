-- member 테이블 생성
CREATE TABLE IF NOT EXISTS member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    createdAt DATE,
updatedAt DATE
);

-- schedule 테이블 생성
CREATE TABLE IF NOT EXISTS schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    title VARCHAR(50) NOT NULL,
    author VARCHAR(50),
    password VARCHAR(50),
    createdAt DATETIME NOT NULL,
    updatedAt DATETIME NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id)
);
