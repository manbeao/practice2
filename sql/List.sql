USE conveniencestoredb;

--  편의점 테이블 생성
DROP TABLE IF EXISTS CONVENIENCE_STORE;
CREATE TABLE IF NOT EXISTS CONVENIENCE_STORE (
                                                 PRODUCT_CODE INT AUTO_INCREMENT COMMENT '상품관리코드',
                                                 PRODUCT_NAME VARCHAR(30) NOT NULL COMMENT '상품이름',
    PRODUCT_PRICE INT NOT NULL COMMENT '상품가격',
    PRODUCT_CATEGORY VARCHAR (30) NOT NULL COMMENT '상품 카테고리',
    PRODUCT_STATUS VARCHAR(5) CHECK(PRODUCT_STATUS IN ('O','X')) COMMENT '재고 여부',
    PRIMARY KEY(PRODUCT_CODE)
    )ENGINE=INNODB;

-- 편의점 테이블에 값 넣기
INSERT INTO CONVENIENCE_STORE(PRODUCT_CODE,PRODUCT_NAME,PRODUCT_PRICE,PRODUCT_CATEGORY,PRODUCT_STATUS) VALUES
                                                                                                           (NULL, '삼각김밥', 1500, '식사', 'O'),
                                                                                                           (NULL, '연세우유크림빵', 2500, '식사', 'O'),
                                                                                                           (NULL, '인기가요 샌드위치', 2000, '식사', 'X'),
                                                                                                           (NULL, '고기듬뿍도시락', 5000, '식사','O'),
                                                                                                           (NULL, '짱구의 된장버터라면', 6000, '식사', 'X'),
                                                                                                           (NULL, '삼다수', 1000, '음료', 'O'),
                                                                                                           (NULL, '바나나 우유단지', 1800, '음료', 'O'),
                                                                                                           (NULL, '콜라', 2000, '음료', 'O'),
                                                                                                           (NULL, '알로에주스', 1500, '음료', 'X'),
                                                                                                           (NULL, '비타500', 1200, '음료', 'O'),
                                                                                                           (NULL, '잭다니엘 허니', 40000, '주류', 'O'),
                                                                                                           (NULL, '아사히 생맥', 4000, '주류', 'X'),
                                                                                                           (NULL, 'KGB 레몬', 3500, '주류', 'O'),
                                                                                                           (NULL, '엑스레이티드', 37000, '주류', 'X'),
                                                                                                           (NULL, '별빛 청하', 1600, '주류', 'O'),
                                                                                                           (NULL, '휴대용 티슈', 1000, '위생용품', 'O'),
                                                                                                           (NULL, '휴대용 칫솔세트', 2500, '위생용품', 'O'),
                                                                                                           (NULL, '비닐장갑', 1500, '위생용품','O'),
                                                                                                           (NULL, '가글', 3000, '위생용품', 'O'),
                                                                                                           (NULL, '일회용 샴푸', 2000, '위생용품', 'O'),
                                                                                                           (NULL, '담배', 4500, '기타', 'O'),
                                                                                                           (NULL, '이어폰', 15500, '기타', 'X'),
                                                                                                           (NULL, '휴대용 충전기', 7000, '기타', 'O'),
                                                                                                           (NULL, '비닐우산', 5000, '기타', 'O'),
                                                                                                           (NULL, '건전지', 4000, '기타', 'O');

-- 관리자 테이블 생성
DROP TABLE IF EXISTS STORE_MANAGER;
CREATE TABLE IF NOT EXISTS STORE_MANAGER(
                                            MANAGER_ID INT PRIMARY KEY AUTO_INCREMENT COMMENT '관리자 ID',
                                            MANAGER_NAME VARCHAR(20) NOT NULL UNIQUE COMMENT '관리자이름',
    MANAGER_PHONE VARCHAR(30) NOT NULL COMMENT '관리자 전화번호',
    PRODUCT_CATEGORY VARCHAR (30) NOT NULL COMMENT '상품 카테고리',
    PRODUCT_CODE INT COMMENT '상품관리코드',
    FOREIGN KEY(PRODUCT_CODE)
    REFERENCES CONVENIENCE_STORE(PRODUCT_CODE)
    )ENGINE=INNODB;

INSERT INTO STORE_MANAGER(MANAGER_ID, MANAGER_NAME, MANAGER_PHONE, PRODUCT_CATEGORY,PRODUCT_CODE) VALUES
                                                                                                      (NULL, '이은서', '01053758297', '식사', 3),
                                                                                                      (NULL, '김민재', '01054929923', '음료', 7),
                                                                                                      (NULL, '김나연', '01054929923', '주류', 12),
                                                                                                      (NULL, '이경주', '01024858297', '위생용품', 16),
                                                                                                      (NULL, '인지예', '01052830765', '기타', 23);

DROP TABLE IF EXISTS STAFF;
CREATE TABLE IF NOT EXISTS STAFF(
                                    STAFF_ID INT PRIMARY KEY AUTO_INCREMENT COMMENT '직원 ID',
                                    STAFF_NAME VARCHAR(30) NOT NULL COMMENT '직원이름',
    STAFF_PHONE VARCHAR(30) NOT NULL COMMENT '직원 전화번호',
    HIRE_DATE VARCHAR(30) NOT NULL COMMENT '입사일',
    WORK_STATUS VARCHAR(5) CHECK(WORK_STATUS IN ('O','X')) COMMENT '출근 여부',
    MANAGER_NAME VARCHAR(20) COMMENT '관리자이름',
    FOREIGN KEY(MANAGER_NAME)
    REFERENCES STORE_MANAGER(MANAGER_NAME)
    )ENGINE=INNODB;

INSERT INTO STAFF(STAFF_ID,STAFF_NAME,STAFF_PHONE,HIRE_DATE,WORK_STATUS,MANAGER_NAME) VALUES
                                                                                          (NULL, '이재현', '01019980913', '2016.04.08', 'O', '김민재'),
                                                                                          (NULL, '황인준', '01020000323', '2016.08.25', 'O', '이은서'),
                                                                                          (NULL, '윤재호', '01046358267', '2018.03.19', 'O', '김나연'),
                                                                                          (NULL, '박상혁', '01019990926', '2020.11.03', 'X', '이은서'),
                                                                                          (NULL, '최호승', '01075641238', '2021.01.18', 'O', '인지예'),
                                                                                          (NULL, '김재범', '01088672343', '2022.05.06', 'O', '이경주'),
                                                                                          (NULL, '윤나무', '01099845389', '2023.06.22', 'X', '이은서');
