INSERT INTO EMPLOYEE (VISA,FIRST_NAME, LAST_NAME, VERSION, BIRTH_DATE)
VALUES
    ('NNN', 'AN' , 'NGUYEN', 1 ,'2020-04-20'),
    ('TTT',  'QUOC', 'HAO' , 2, '2020-04-25');
INSERT INTO "group" (GROUP_LEADER_ID,VERSION)
VALUES
    (1, 1),
    (2, 2);
INSERT INTO PROJECT (PROJECT_NUMBER, NAME, STATUS, CUSTOMER, START_DATE, END_DATE, GROUP_ID)
VALUES
    (5, 'EFV', 'New' , 'JOHN' ,'2020-04-20','2020-04-25',1),
    (2, 'CXTRANET', 'Inprogress' , 'ANNA', '2020-04-25','2020-04-28',2 ),
    (3, 'CRYSTAL BALL', 'New' , 'ATHUR', '2020-04-28', '2020-06-07',1),
    (4, 'IOC CLIENT EXTRANET', 'Finished' , 'BEN', '2020-06-07', '2020-06-08',2),
    (1, 'KSTA MIGRATION', 'Planned' , 'BRUCE' , '2020-06-08','2020-04-25',2);


