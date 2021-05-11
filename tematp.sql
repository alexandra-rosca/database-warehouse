drop database tematp;
create database tematp;

select * from client;
select * from ordertable;
select * from product;

insert into client values (1, 'Maria', 'Baritiu');
insert into ordertable values (1, 3, 'Maria', 'Ciocolata', 1);
insert into ordertable values (2, 4, 'Maria', 'Oua', 2);
insert into product values (1, 'Ciocolata', 5, 10);

SELECT  *  FROM Product WHERE id = 2;

create table ordertable (
id int not null auto_increment,
primary key(id),
orderQuantity int not null,
clientName varchar(45),
productName varchar(45),
idProduct int not null,
foreign key(idProduct) references product(id) );
select * from ordertable where idProduct = 1;

drop table ordertable;
select * from orders;

