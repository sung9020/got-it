DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS brand;

CREATE TABLE category (
                          category_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리 아이디',
                          name VARCHAR(255) NOT NULL COMMENT '카테고리 이름',
                          modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자'
) COMMENT '카테고리 테이블';

CREATE TABLE brand (
                       brand_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리 아이디',
                       name VARCHAR(255) NOT NULL COMMENT '브랜드 이름',
                       modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자'
) COMMENT '브랜드 테이블';

CREATE TABLE product (
                         product_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '상품 아이디',
                         price DECIMAL(19,2) NOT NULL COMMENT '가격',
                         category_id BIGINT COMMENT '상품 카테고리',
                         brand_id BIGINT COMMENT '브랜드',
                         modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
                         FOREIGN KEY (category_id) REFERENCES category(category_id),
                         FOREIGN KEY (brand_id) REFERENCES brand(brand_id)
) COMMENT '상품 테이블';

ALTER TABLE category
    ADD CONSTRAINT uk_category_name UNIQUE KEY (name);

ALTER TABLE brand
    ADD CONSTRAINT uk_brand_name UNIQUE KEY (name);