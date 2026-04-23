CREATE TABLE categories (
    category_id NUMBER PRIMARY KEY,
    category_name VARCHAR2(50) NOT NULL UNIQUE
);

CREATE TABLE study_posts (
    post_id NUMBER PRIMARY KEY,
    user_id NUMBER NOT NULL,
    category_id NUMBER,
    title VARCHAR2(200) NOT NULL,
    content CLOB NOT NULL,
    max_members NUMBER NOT NULL,
    status VARCHAR2(20) DEFAULT 'OPEN' NOT NULL,
    view_count NUMBER DEFAULT 0 NOT NULL,
    created_at DATE DEFAULT SYSDATE NOT NULL,
    updated_at DATE DEFAULT SYSDATE NOT NULL,
    CONSTRAINT fk_study_posts_user
        FOREIGN KEY (user_id) REFERENCES user_account(user_id),
    CONSTRAINT fk_study_posts_category
        FOREIGN KEY (category_id) REFERENCES categories(category_id),
    CONSTRAINT ck_study_posts_status
        CHECK (status IN ('OPEN', 'CLOSED'))
);

CREATE TABLE study_applications (
    application_id NUMBER PRIMARY KEY,
    post_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL,
    message VARCHAR2(300),
    status VARCHAR2(20) DEFAULT 'PENDING' NOT NULL,
    created_at DATE DEFAULT SYSDATE NOT NULL,
    CONSTRAINT fk_study_app_post
        FOREIGN KEY (post_id) REFERENCES study_posts(post_id),
    CONSTRAINT fk_study_app_user
        FOREIGN KEY (user_id) REFERENCES user_account(user_id),
    CONSTRAINT uq_study_app UNIQUE (post_id, user_id),
    CONSTRAINT ck_study_app_status
        CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED'))
);

CREATE TABLE comments (
    comment_id NUMBER PRIMARY KEY,
    post_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL,
    content VARCHAR2(500) NOT NULL,
    created_at DATE DEFAULT SYSDATE NOT NULL,
    updated_at DATE DEFAULT SYSDATE NOT NULL,
    CONSTRAINT fk_comments_post
        FOREIGN KEY (post_id) REFERENCES study_posts(post_id),
    CONSTRAINT fk_comments_user
        FOREIGN KEY (user_id) REFERENCES user_account(user_id)
);

CREATE SEQUENCE seq_categories START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_study_posts START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_study_applications START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_comments START WITH 1 INCREMENT BY 1 NOCACHE;