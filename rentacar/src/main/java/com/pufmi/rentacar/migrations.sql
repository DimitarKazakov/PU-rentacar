CREATE TABLE CLIENTS (
   ID INT PRIMARY KEY AUTO_INCREMENT,
   IS_DELETED BOOLEAN DEFAULT false,
   CREATED_AT DATETIME DEFAULT CURRENT_TIMESTAMP,
   NAME VARCHAR(255) NOT NULL,
   ADDRESS VARCHAR(255) NOT NULL,
   PHONE VARCHAR(255) NOT NULL,
   AGE INT NOT NULL,
   HAS_INCIDENTS BOOLEAN NOT NULL
);

CREATE TABLE CARS (
   ID INT PRIMARY KEY AUTO_INCREMENT,
   IS_DELETED BOOLEAN DEFAULT false,
   CREATED_AT DATETIME DEFAULT CURRENT_TIMESTAMP,
   MAKE VARCHAR(255) NOT NULL,
   MODEL VARCHAR(255) NOT NULL,
   LOCATION VARCHAR(255) NOT NULL,
   PRICE_PER_DAY DOUBLE NOT NULL
);

CREATE TABLE OFFERS (
   ID INT PRIMARY KEY AUTO_INCREMENT,
   IS_DELETED BOOLEAN DEFAULT false,
   CREATED_AT DATETIME DEFAULT CURRENT_TIMESTAMP,
   ACCEPTED BOOLEAN DEFAULT false,
   START_DATE DATE NOT NULL,
   ENT_DATE DATE NOT NULL,
   FINAL_PRICE DOUBLE NOT NULL,
   CLIENT_ID INT NOT NULL,
   CAR_ID INT NOT NULL,
   FOREIGN KEY (CLIENT_ID) REFERENCES CLIENTS(ID),
   FOREIGN KEY (CAR_ID) REFERENCES CARS(ID)
);

INSERT INTO CARS ( MAKE, MODEL , LOCATION , PRICE_PER_DAY )
VALUES
('Toyota', 'Corolla', 'Plovdiv', 70.50),
('Toyota', 'Camry', 'Plovdiv', 105.50),
('Toyota', 'Yaris', 'Plovdiv', 50.50),
('Ford', 'Fiesta', 'Sofia', 45.50),
('Ford', 'Focus', 'Sofia', 85.50),
('Ford', 'Mondeo', 'Sofia', 102.50),
('BMW', 'X5', 'Varna', 150.50),
('BMW', 'E30', 'Varna', 135.50),
('BMW', 'Z1', 'Varna', 170.50),
('Mercedes', 'E250', 'Burgas', 120.50),
('Mercedes', 'S500', 'Burgas', 250.05),
('Mercedes', 'G class', 'Burgas', 200.01),
('Volvo', 'V40', 'Plovdiv', 66.50),
('Volvo', 'XC90', 'Sofia', 230.50),
('Volvo', 'S70', 'Burgas', 120.50),
('Nissan', 'Micra', 'Plovdiv', 35.50),
('Honda', 'Civic', 'Sofia', 55.50),
('Honda', 'CRV', 'Varna', 88.50);