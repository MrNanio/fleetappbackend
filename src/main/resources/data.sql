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












