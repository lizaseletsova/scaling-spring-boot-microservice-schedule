
CREATE TABLE MESSAGE (
                      id BIGINT AUTO_INCREMENT  PRIMARY KEY,
                      username VARCHAR(250) NOT NULL,
                      time_Of_the_message VARCHAR(250) NOT NULL,
                      value_message VARCHAR(250) DEFAULT NULL
);

CREATE TABLE SHEDLOCK (
                          name VARCHAR(64),
                          lock_until TIMESTAMP(3) NULL,
                          locked_at TIMESTAMP(3) NULL,
                          locked_by VARCHAR(255),
                          PRIMARY KEY (name)
)