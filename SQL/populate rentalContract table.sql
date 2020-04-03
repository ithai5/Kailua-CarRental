INSERT INTO RentalContract (startDate, endDate, maxKm, startKm, licencePlate, customer_id) VALUES
('2020-04-01 10:30:00', '2020-04-14 18:30:00', 1200, 10653, "bg99778", 3),
('2020-12-20 15:45:00', '2021-01-04 12:00:00', null, 2800, "ab54543", 7),
('2020-06-29 05:30:00', '2020-07-09 23:15:00', 700, 7983, "cp34213", 5),
('2020-09-11 16:15:00', '2020-09-15 20:00:00', 300, 19000, "kl77863", 1),
('2020-05-09 10:45:00', '2020-05-19 13:00:00', 1500, 10023, "xy98762", 4);

SELECT * FROM KeaProject.RentalContract;
SELECT * FROM KeaProject.CarInfo;