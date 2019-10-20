insert into users (user_type,id, email, name,phone_number,username,password,address,photo) values
('c',1, 'fancyEmail@gmail.com', 'Rui',961234567,'Rui123','password','Rua Da Esquerda',null),
('c',2, 'uglyEmail@gmail.com', 'Pedro',960425362,'Pedrocas','password','Rua Da Direita',null);

insert into clients (id) values
(1),
(2);

insert into pets (id,age,medical_record,notes,photo,physical_description,species,owner_id) values
(1,4,'Medical Stuff','Pretty dog',null,'Small dog','Bulldog',1),
(2,2,'Other medical stuff','Is a happy pet',null,'Small cat','Cat',1);
