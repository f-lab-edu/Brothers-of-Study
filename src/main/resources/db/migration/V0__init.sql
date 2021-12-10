CREATE TABLE IF NOT EXISTS users
(
    id              BIGINT          NOT NULL        AUTO_INCREMENT PRIMARY KEY,
    email           VARCHAR(128)    NOT NULL,
    password        VARCHAR(128)    NOT NULL,
    name            VARCHAR(128)    NOT NULL,
    address         VARCHAR(128)    NOT NULL,
    created_at      TIMESTAMP       DEFAULT NOW(),
    updated_at      TIMESTAMP       DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS study_cafes
(
    id              BIGINT          NOT NULL        AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    title           VARCHAR(128)    NOT NULL,
    address         VARCHAR(128)    NOT NULL,
    thumbnail       TEXT            DEFAULT NULL,
    created_at      TIMESTAMP       DEFAULT NOW(),
    updated_at      TIMESTAMP       DEFAULT NOW()

#     FOREIGN KEY (user_id) REFERENCES users (id)
);
