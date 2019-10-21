insert into users (id, email, name,phone_number,username,password,address,photo) values
(1, 'fancyEmail@gmail.com', 'Rui',961234567,'Rui123','password','Rua Da Esquerda',null),
(2, 'workingEmail@gmail.com', 'Lara',961234567,'Laria','password','Rua Da Frente',null),
(3, 'uglyEmail@gmail.com', 'Pedro',960425362,'Pedrocas','password','Rua Da Direita',null);

insert into clients (id) values
(1),
(3);

insert into employees (id) values
(2);

insert into veterinarians (enabled,id) values
(true, 2);

insert into pets (id,age,medical_record,notes,photo,physical_description,species,owner_id) values
(1,4,'Medical Stuff','Pretty dog',null,'Small dog','Bulldog',1),
(2,2,'Other medical stuff','Is a happy pet',null,'Small cat','Cat',1);
