/*
CREATE TABLE IF NOT EXISTS Car (
	licencePlate 	VARCHAR (15) 	PRIMARY KEY,
	brand 	VARCHAR (45)	 NOT NULL,
	model 	VARCHAR (20)	 NOT NULL,
	registration	 DATE 	NOT NULL,
	odometer 	INT 	NOT NULL,
	fuelType 	VARCHAR (10) 	NOT NULL,
	features_id INT ,Features_ibfk_1
	FOREIGN KEY (features_id)
		REFERENCES KeaProject.Features (features_id)
		ON DELETE SET NULL
)

CREATE TABLE IF NOT EXISTS rentalContract (
	rentalContract_id 	INT	AUTO_INCREMENT PRIMARY KEY,
    startDate 	DATETIME	 NOT NULL,
    endDate 	DATETIME 	NOT	NULL,
    maxKm 	INT,
    startKm 	INT,
	licencePlate VARCHAR(15),
    customer_id INT NOT NULL,
    FOREIGN KEY (licencePlate)
		REFERENCES KeaProject.Car (licencePlate)
        ON DELETE SET NULL,
    FOREIGN KEY (customer_id)
		REFERENCES KeaProject.Customer (customer_id)
) ;



ALTER TABLE KeaProject.RentalContract
ADD COLUMN pricePerDay int AFTER className;
-- SELECT * FROM KeaProject.ClassTypeFeatures

UPDATE ClassType 
SET pricePerDay = 439 WHERE  className_id = 2;
*/ 
  SELECT * FROM KeaProject.Features;
  INSERT INTO KeaProject.Features (ccm,gear, ariCondition, cruiseControl,leatherSeat, seatNumber. horsePower, className_id)
  VALUES 
  (1900,'manual',1,0,7,190,2),
  -- B-Class Compact MPV  B 220 	Petrol	190 HP/ 140 KW	Front-Wheel Drive	1991 ccm	
  (2200,'manual', 1, 1,7,200,2),
  -- chevrolet orlando 
  (2000, 'automatic',1,1,5,240,1),
  -- audi a6
  (3900,'automatic',1,1,1,4,550,1),
  -- porche panamera
  (1700, 'manual',1,1,1,240,3),
  --  alfa romeo 4c
  (2000,'manual',1,1,1,420,3)
  -- porche 911


SELECT * FROM ClassType