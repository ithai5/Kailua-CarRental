DROP TABLE IF EXISTS Address;

SELECT * FROM KeaProject.Customer;

CREATE TABLE Address (
	address_id	INT				AUTO_INCREMENT		PRIMARY KEY,
    country		VARCHAR(20)		NOT NULL,
    city		VARCHAR(40),
    street		VARCHAR(40),
    houseNumber	VARCHAR(10),
    zipcode		VARCHAR(10)		NOT NULL,
    customer_id	INT,
    FOREIGN KEY (customer_id)		REFERENCES KeaProject.Customer(customer_id)
    ON DELETE CASCADE
);

INSERT INTO Address (country, city, street, houseNumber, zipcode, customer_id) VALUES
("Argentina", "Ningún citio", "Calle de los Gangstas", "2A", "99999", 1),
("North America", "Detroit", "8 Mile St.", "50C", "48127", 2),
("North America", "Cole World LA", "Hills Ave.", "214", "90001", 3),
("North America", "Weed City", "Green Street", "420", "51420", 4),
("North America", "Los Angeles", "Compton Walk", "112A", "90220", 5),
("West World", "Kanye City", "Ye Blvd.", "1", "55512", 6),
("North America", "Los Angeles", "Compton Walk", "112B", "90220", 7);

SELECT * FROM KeaProject.Address;