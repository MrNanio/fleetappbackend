REPLACE INTO `roles` VALUES (1,'ROLE_USER');
REPLACE INTO `roles` VALUES (2,'ROLE_SUPERUSER');
REPLACE INTO `roles` VALUES (3,'ROLE_ADMIN');
REPLACE INTO `user_account_status` VALUES (1,'ACTIVE');
REPLACE INTO `user_account_status` VALUES (2,'INACTIVE');
REPLACE INTO `user_account_status` VALUES (3,'BLOCKED');
REPLACE INTO `insurance_types` VALUES (1,'OC');
REPLACE INTO `insurance_types` VALUES (2,'AC');
REPLACE INTO `insurance_types` VALUES (3,'NNW');
REPLACE INTO `vehicle_status` VALUES (1,'Aktywny');
REPLACE INTO `vehicle_status` VALUES (2,'W naprawie');
REPLACE INTO `vehicle_status` VALUES (3,'');
REPLACE INTO `vehicle_makes` VALUES (1,'Abarth');
REPLACE INTO `vehicle_makes` VALUES (2,'Alfa Romeo');
REPLACE INTO `vehicle_makes` VALUES (3,'Aston Martin');
REPLACE INTO `vehicle_makes` VALUES (4,'Audi');
REPLACE INTO `vehicle_makes` VALUES (5,'Bentley');
REPLACE INTO `vehicle_makes` VALUES (6,'BMW');
REPLACE INTO `vehicle_makes` VALUES (7,'Bugatti');
REPLACE INTO `vehicle_makes` VALUES (8,'Cadillac');
REPLACE INTO `vehicle_makes` VALUES (9,'Chevrolet');
REPLACE INTO `vehicle_makes` VALUES (10,'Chrysler');
REPLACE INTO `vehicle_makes` VALUES (11,'Citroen');
REPLACE INTO `vehicle_makes` VALUES (12,'Daewoo');
REPLACE INTO `vehicle_makes` VALUES (13,'Daihatsu');
REPLACE INTO `vehicle_makes` VALUES (14,'Dodge');
REPLACE INTO `vehicle_makes` VALUES (15,'Ferrari');
REPLACE INTO `vehicle_makes` VALUES (16,'Fiat');
REPLACE INTO `vehicle_makes` VALUES (17,'Ford');
REPLACE INTO `vehicle_makes` VALUES (18,'FSO');
REPLACE INTO `vehicle_makes` VALUES (19,'Honda');
REPLACE INTO `vehicle_makes` VALUES (20,'Hummer');
REPLACE INTO `vehicle_makes` VALUES (21,'Hyundai');
REPLACE INTO `vehicle_makes` VALUES (22,'Jaguar');
REPLACE INTO `vehicle_makes` VALUES (23,'Jeep');
REPLACE INTO `vehicle_makes` VALUES (24,'Kia');
REPLACE INTO `vehicle_makes` VALUES (25,'Koenigsegg');
REPLACE INTO `vehicle_makes` VALUES (26,'Lamborghini');
REPLACE INTO `vehicle_makes` VALUES (27,'Lancia');
REPLACE INTO `vehicle_makes` VALUES (28,'Land Rover');
REPLACE INTO `vehicle_makes` VALUES (29,'Lexus');
REPLACE INTO `vehicle_makes` VALUES (30,'Łada');
REPLACE INTO `vehicle_makes` VALUES (31,'Lotus');
REPLACE INTO `vehicle_makes` VALUES (32,'Lincoln');
REPLACE INTO `vehicle_makes` VALUES (33,'Maserati');
REPLACE INTO `vehicle_makes` VALUES (34,'Maybach');
REPLACE INTO `vehicle_makes` VALUES (35,'Mazda');
REPLACE INTO `vehicle_makes` VALUES (36,'McLaren');
REPLACE INTO `vehicle_makes` VALUES (37,'Mercedes-Benz');
REPLACE INTO `vehicle_makes` VALUES (38,'MG');
REPLACE INTO `vehicle_makes` VALUES (39,'Mini');
REPLACE INTO `vehicle_makes` VALUES (40,'Mitsubishi');
REPLACE INTO `vehicle_makes` VALUES (41,'Nissan');
REPLACE INTO `vehicle_makes` VALUES (42,'Opel');
REPLACE INTO `vehicle_makes` VALUES (43,'Peugeot');
REPLACE INTO `vehicle_makes` VALUES (44,'Porsche');
REPLACE INTO `vehicle_makes` VALUES (45,'Renault');
REPLACE INTO `vehicle_makes` VALUES (46,'Rolls-Royce');
REPLACE INTO `vehicle_makes` VALUES (47,'Saab');
REPLACE INTO `vehicle_makes` VALUES (48,'Rover');
REPLACE INTO `vehicle_makes` VALUES (49,'Seat');
REPLACE INTO `vehicle_makes` VALUES (50,'Skoda');
REPLACE INTO `vehicle_makes` VALUES (51,'Smart');
REPLACE INTO `vehicle_makes` VALUES (52,'SsangYong');
REPLACE INTO `vehicle_makes` VALUES (53,'Subaru');
REPLACE INTO `vehicle_makes` VALUES (54,'Suzuki');
REPLACE INTO `vehicle_makes` VALUES (55,'Syrena');
REPLACE INTO `vehicle_makes` VALUES (56,'TATA');
REPLACE INTO `vehicle_makes` VALUES (57,'UAZ');
REPLACE INTO `vehicle_makes` VALUES (58,'Toyota');
REPLACE INTO `vehicle_makes` VALUES (59,'Trabant');
REPLACE INTO `vehicle_makes` VALUES (60,'Volkswagen');
REPLACE INTO `vehicle_makes` VALUES (61,'Volvo');
REPLACE INTO `vehicle_makes` VALUES (62,'Wartburg');
REPLACE INTO `vehicle_makes` VALUES (63,'Dacia');
REPLACE INTO `vehicle_makes` VALUES (64,'Infiniti');
REPLACE INTO `fuel_types` VALUES (1,'LPG');
REPLACE INTO `fuel_types` VALUES (2,'PB98');
REPLACE INTO `fuel_types` VALUES (3,'PB95');
REPLACE INTO `fuel_types` VALUES (4,'ON');
-- USERS ACCOUNT ACCOUNT_PASSWD admin1234
REPLACE INTO `users` VALUES (1, NULL, 'admin@admin.xd', b'1', NULL, '$2a$10$f8xGjebqsOXMPbyBYFqXPuSV8AoqKGtvIB0EmBMCX2zJPY0ZaTKC6', NULL, NULL, '3', NULL, '1');
REPLACE INTO `users` VALUES (2, NULL, 'superuser@superuser.xd', b'1', NULL, '$2a$10$f8xGjebqsOXMPbyBYFqXPuSV8AoqKGtvIB0EmBMCX2zJPY0ZaTKC6', NULL, NULL, '2', NULL, '1');
REPLACE INTO `users` VALUES (3, NULL, 'user@user.xd', b'1', NULL, '$2a$10$f8xGjebqsOXMPbyBYFqXPuSV8AoqKGtvIB0EmBMCX2zJPY0ZaTKC6', NULL, NULL, '1', '2', '1');
-- VEHICLES
REPLACE INTO `vehicles` VALUES (1, '8', '12', 'CZERWONY', '3', '70000', 'KUGA', 'LU00001', '2G1WB5EK6B1328213', '2010', '1', '2', 17, '1');
REPLACE INTO `vehicles` VALUES (2, '6', '22', 'BIAŁY', '4', '69800', 'A1', 'LU00002', '1D4HB48N36F145305', '2002', '2', '2', 4, '1');
REPLACE INTO `vehicles` VALUES (3, '4', '14', 'ZIELONY', '3', '85478', 'CLIO', 'LU00003', '2GCEC19T921387123', '2012', '3', '2', 45, '1');
REPLACE INTO `vehicles` VALUES (4, '7', '13', 'CZARNY', '5', '120587', 'SORENTO', 'LU00004', '1B3HB48B78D524276', '2000', '1', '2', 24, '1');
REPLACE INTO `vehicles` VALUES (5, '6', '9', 'RÓZOWY', '4', '254000', 'DUSTER', 'LU00005', '5FNRL3H49AB112435', '2005', '2', '2', 63, '1');
REPLACE INTO `vehicles` VALUES (6, '8', '9', 'SREBRNY', '6', '55789', 'FOCUS', 'LU00006', '1G2WJ12MXSF226774', '2009', '3', '2', 17, '1');
REPLACE INTO `vehicles` VALUES (7, '5', '8', 'BIAŁY', '7', '210580', 'A2', 'LU00007', '2HGFG12697H551239', '2010', '1', '2', 4, '1');
REPLACE INTO `vehicles` VALUES (8, '3', '5', 'BIAŁY', '5', '99580', 'OCTAVIA', 'LU00008', 'JTMBK33V275024552', '2013', '1', '2', 50, '1');
REPLACE INTO `vehicles` VALUES (9, '5', '8', 'CZARNY', '6', '45024', 'A3', 'LU00009', '5XXGR4A63CG025847', '2011', '2', '2', 4, '1');
REPLACE INTO `vehicles` VALUES (10, '6', '9', 'NIEBIESKI', '7', '65421', 'A8', 'LU00010', '4T1FA38P28U157684', '2019', '2', '2', 4, '1');
REPLACE INTO `vehicles` VALUES (11, '5', '11', 'ZIELONY', '6', '5078', 'MONDEO', 'LU00011', 'JM3KE4DY3F0496171', '2002', '3', '2', 17, '1');
REPLACE INTO `vehicles` VALUES (12, '4', '5', 'BIAŁY', '5', '1500', 'SUPERB', 'LU00012', 'JN8AS5MV7AW612281', '2009', '1', '2', 50, '1');
REPLACE INTO `vehicles` VALUES (13, '8', '9', 'BIAŁY', '6', '14225', 'A4', 'LU00013', '5FNRL5H94FB041109', '2014', '1', '2', 4, '1');
REPLACE INTO `vehicles` VALUES (14, '8', '12', 'CZERWONY', '3', '70100', 'ESCORT', 'LU00014', '3VWTD81H9WM262882', '2011', '1', '2', 17, '1');
REPLACE INTO `vehicles` VALUES (15, '6', '22', 'BIAŁY', '4', '69800', 'A1', 'LU00015', 'JN8AZ18U29W166218', '2003', '4', '2', 4, '1');
REPLACE INTO `vehicles` VALUES (16, '4', '14', 'ZIELONY', '3', '85478', 'MEGANE', 'LU00016', '2FMDK3GC4CBA70161', '2013', '3', '2', 45, '1');
REPLACE INTO `vehicles` VALUES (17, '7', '13', 'CZARNY', '5', '120587', 'SORENTO', 'LU00017', '2C3CDXBG2CH110153', '2001', '1', '2', 24, '1');
REPLACE INTO `vehicles` VALUES (18, '6', '9', 'RÓZOWY', '4', '254000', 'SANDERO', 'LU00018', '2HGFB2F58DH520338', '2006', '2', '2', 63, '1');
REPLACE INTO `vehicles` VALUES (19, '8', '9', 'SREBRNY', '6', '55789', 'FOCUS', 'LU00019', '1B3CC4FB2AN188739', '2010', '4', '2', 17, '1');
REPLACE INTO `vehicles` VALUES (20, '5', '8', 'BIAŁY', '7', '210580', 'A2', 'LU00020', 'JM3ER293180155079', '2011', '1', '2', 4, '1');
REPLACE INTO `vehicles` VALUES (21, '3', '5', 'BIAŁY', '5', '99580', 'OCTAVIA', 'LU00021', 'KMHDU4AD6AU983559', '2014', '1', '2', 50, '1');
REPLACE INTO `vehicles` VALUES (22, '5', '8', 'CZARNY', '6', '45024', 'A3', 'LU00022', '1YVHZ8DH8C5M41593', '2012', '4', '2', 4, '1');
REPLACE INTO `vehicles` VALUES (23, '6', '9', 'NIEBIESKI', '7', '65421', 'A8', 'LU00023', '1GCEK14T45Z144938', '2020', '2', '2', 4, '1');
REPLACE INTO `vehicles` VALUES (24, '5', '11', 'ZIELONY', '6', '5078', 'MONDEO', 'LU00024', '1FTNW21L94EA86572', '2003', '3', '2', 17, '1');
REPLACE INTO `vehicles` VALUES (25, '4', '5', 'BIAŁY', '5', '1500', 'FABIA', 'LU00025', '6MPCT0365N8644356', '2010', '1', '2', 50, '1');
REPLACE INTO `vehicles` VALUES (26, '8', '9', 'BIAŁY', '6', '14225', 'A6', 'LU00026', '1C4RJFBG7EC200798', '2015', '1', '2', 4, '1');
REPLACE INTO `vehicles` VALUES (27, '6', '9', 'NIEBIESKI', '7', '65421', 'A8', 'LU00027', '1FTWW31P86EA42319', '2017', '4', '2', 4, '1');
REPLACE INTO `vehicles` VALUES (28, '5', '11', 'ZIELONY', '6', '5078', 'MONDEO', 'LU00028', '2FMDK38C28BB54940', '2005', '3', '2', 17, '1');
REPLACE INTO `vehicles` VALUES (29, '4', '5', 'BIAŁY', '5', '1500', 'SUPERB', 'LU00029', '1GNDT13W82K112493', '2010', '4', '2', 50, '1');
REPLACE INTO `vehicles` VALUES (30, '8', '9', 'BIAŁY', '6', '14225', 'A1', 'LU00030', 'KMHGN4JE7FU051641', '2015', '1', '2', 4, '1');
-- VEHICLE INSPECTION
REPLACE INTO `vehicle_inspections` VALUES (1,'450.67', 'brak uwag', '2021-11-08', '2020-11-08', '1');
REPLACE INTO `vehicle_inspections` VALUES (2,'250.00', 'brak uwag', '2021-11-08', '2020-11-08', '2');
REPLACE INTO `vehicle_inspections` VALUES (3,'150.23', 'brak uwag', '2021-11-08', '2020-11-08', '3');
REPLACE INTO `vehicle_inspections` VALUES (4,'50.60', 'brak uwag', '2021-11-08', '2020-11-08', '4');
REPLACE INTO `vehicle_inspections` VALUES (5,'99.67', 'brak uwag', '2021-11-08', '2020-11-08', '5');
REPLACE INTO `vehicle_inspections` VALUES (6,'450.67', 'brak uwag', '2020-11-08', '2019-11-08', '1');
REPLACE INTO `vehicle_inspections` VALUES (7,'250.00', 'brak uwag', '2020-11-08', '2019-11-08', '2');
REPLACE INTO `vehicle_inspections` VALUES (8,'150.23', 'brak uwag', '2020-11-08', '2019-11-08', '3');
REPLACE INTO `vehicle_inspections` VALUES (9,'50.60', 'brak uwag', '2020-11-08', '2019-11-08', '4');
REPLACE INTO `vehicle_inspections` VALUES (10,'99.67', 'brak uwag', '2020-11-08', '2019-11-08', '5');
-- VEHICLE REFUELING
REPLACE INTO `vehicle_refueling` VALUES (1, '450', 'NOWY', '80', '2020-11-18', 2, 6);
REPLACE INTO `vehicle_refueling` VALUES (2, '150', 'NOWY', '30', '2020-09-18', 2, 5);
REPLACE INTO `vehicle_refueling` VALUES (3, '50.22', 'NOWY', '10', '2020-11-18', 2, 6);
REPLACE INTO `vehicle_refueling` VALUES (4, '40.88', 'NOWY', '5', '2019-11-18', 2, 5);
REPLACE INTO `vehicle_refueling` VALUES (5, '45.98', 'NOWY', '6', '2020-10-18', 2, 3);
REPLACE INTO `vehicle_refueling` VALUES (6, '89.90', 'NOWY', '20', '2019-11-18', 2, 8);
REPLACE INTO `vehicle_refueling` VALUES (7, '120.99', 'NOWY', '25', '2020-11-18', 2, 8);
REPLACE INTO `vehicle_refueling` VALUES (8, '130.67', 'NOWY', '30', '2020-11-18', 2, 8);
REPLACE INTO `vehicle_refueling` VALUES (9, '450.99', 'NOWY', '60', '2019-11-18', 2, 5);
REPLACE INTO `vehicle_refueling` VALUES (10, '45.00', 'NOWY', '10', '2020-09-18', 2, 9);
-- VEHICLE USE
REPLACE INTO `vehicle_use` VALUES (1, 'Lublin-Warszawa', '156', '2020-11-05', 'average', 2, 1);
REPLACE INTO `vehicle_use` VALUES (2, 'Lublin-Łuków', '56', '2020-11-05', 'country', 2, 2);
REPLACE INTO `vehicle_use` VALUES (3, 'Lublin-Rzeszów', '256', '2020-11-04', 'average', 2, 2);
REPLACE INTO `vehicle_use` VALUES (4, 'Lublin-Rzeszów', '220', '2020-11-12', 'city', 2, 2);
REPLACE INTO `vehicle_use` VALUES (5, 'Lublin-Kraków-Lublin', '870', '2020-11-15', 'average', 2, 7);
REPLACE INTO `vehicle_use` VALUES (6, 'Lublin-Wrocław-Karpacz-Łódz-Lublin', '1506', '2020-11-11', 'average', 2, 6);
REPLACE INTO `vehicle_use` VALUES (7, 'Lublin-Warszawa', '159', '2020-11-10', 'average', 2, 6);
REPLACE INTO `vehicle_use` VALUES (8, 'Warszawa-Lublin', '160', '2020-11-18', 'average', 2, 4);
REPLACE INTO `vehicle_use` VALUES (9, 'Lublin-Kraków-Lublin', '780', '2020-11-12', 'average', 2, 4);
REPLACE INTO `vehicle_use` VALUES (10, 'Lublin-Gdańsk', '430', '2020-11-05', 'average', 2, 12);
REPLACE INTO `vehicle_use` VALUES (11, 'Lublin-Rzeszów', '100', '2020-11-14', 'average', 2, 2);
REPLACE INTO `vehicle_use` VALUES (12, 'Lublin-Rzeszów', '50', '2020-11-13', 'city', 2, 2);
-- VEHICLE INSURANCE
REPLACE  INTO `vehicle_insurances` VALUES (1, '1500','ubezpieczenie ac+oc WARTA ', '2019-11-05', '2020-11-05', '6710902457960012126533076', 2, 2);
REPLACE  INTO `vehicle_insurances` VALUES (2, '2500','ubezpieczenie ac+oc PZU', '2020-11-05', '2021-11-05', '9881400009096721648841155', 2, 2);
REPLACE  INTO `vehicle_insurances` VALUES (3, '500','ubezpieczenie ac+oc AXA', '2020-11-05', '2021-11-05', '5810205659974710846196921', 2, 3);
REPLACE  INTO `vehicle_insurances` VALUES (4, '1220','ubezpieczenie ac+oc WARTA', '2020-11-05', '2021-11-05', '0782100001070318944317636', 2, 4);
REPLACE  INTO `vehicle_insurances` VALUES (5, '980','ubezpieczenie ac+oc PZU', '2019-11-05', '2020-11-05', '6115001878091093477211182', 2, 5);
REPLACE  INTO `vehicle_insurances` VALUES (6, '3400','ubezpieczenie oc AXA', '2019-11-05', '2021-11-05', '5488110006797668242955097', 2, 12);
REPLACE  INTO `vehicle_insurances` VALUES (7, '1500','ubezpieczenie ac+oc HESTIA', '2020-11-05', '2021-11-05', '6912404214376027415238526', 2, 22);
REPLACE  INTO `vehicle_insurances` VALUES (8, '1348','ubezpieczenie ac+oc PZU', '2020-11-05', '2021-11-05', '3112402988516329744734649', 2, 21);
REPLACE  INTO `vehicle_insurances` VALUES (9, '1100','ubezpieczenie oc WARTA', '2020-11-05', '2021-11-05', '6981701018308927672279411', 2, 4);
REPLACE  INTO `vehicle_insurances` VALUES (10, '1234','ubezpieczenie oc XD', '2020-11-05', '2021-11-05', '8110203958893241318910172', 2, 14);
-- VEHICLE REPAIR
REPLACE INTO `vehicle_repairs` VALUES (1, '1200.00', 'naprawa katalizatora', '2020-11-02', 'katalizator', 2);
REPLACE INTO `vehicle_repairs` VALUES (2, '200.00', 'naprawa oświetlenia', '2020-11-02', 'oświetlenie', 3);
REPLACE INTO `vehicle_repairs` VALUES (3, '1100.90', 'wymiana rozrządu', '2020-11-02', 'rozrząd', 4);
REPLACE INTO `vehicle_repairs` VALUES (4, '1450.00', 'wymiana rozrządu', '2020-11-02', 'rozrząd', 5);
REPLACE INTO `vehicle_repairs` VALUES (5, '999.99', 'wymiana chłodnicy', '2020-11-02', 'chłodnica', 6);
REPLACE INTO `vehicle_repairs` VALUES (6, '890.90', 'naprawa alternatora', '2020-11-02', 'alternator', 7);
REPLACE INTO `vehicle_repairs` VALUES (7, '120.99', 'naprawa fotela', '2020-11-02', 'fotel', 6);
REPLACE INTO `vehicle_repairs` VALUES (8, '4500.99', 'naprawa skrzyni biegów', '2020-11-02', 'biegi', 3);
REPLACE INTO `vehicle_repairs` VALUES (9, '1890.00', 'wymiana wahaczy', '2020-11-02', 'zawieszenie', 9);
REPLACE INTO `vehicle_repairs` VALUES (10, '1211.90', 'wymiana akumulatora, żarówek, koła', '2020-11-02', 'różne', 12);











