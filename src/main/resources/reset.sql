set foreign_key_checks = 0;


TRUNCATE TABLE Cookies;
TRUNCATE TABLE RawMaterials;
TRUNCATE TABLE Customers;
TRUNCATE TABLE Recipes;
TRUNCATE TABLE Orders;
TRUNCATE TABLE Pallets;




insert into Cookies ( name ) values ('Amneris');
insert into Cookies ( name ) values ('Berliner');
insert into Cookies ( name ) values ('Nut cookie');
insert into Cookies ( name ) values ('Nut ring');
insert into Cookies ( name ) values ('Tango');
insert into Cookies ( name ) values ('Almond delight');



insert into RawMaterials(name, amount , unit) values ('Bread crumbs', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Butter', 500000 , 'g' );
insert into RawMaterials(name, amount , unit) values ('Chocolate', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Chopped almonds', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Cinnamon', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Egg whites', 500000 , 'ml');
insert into RawMaterials(name, amount , unit) values ('Eggs', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Fine-ground nuts', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Flour', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Ground, roasted nuts', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Icing sugar', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Marzipan', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Potato starch', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Roasted, chopped nuts', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Sodium bicarbonate', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Sugar', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Vanilla sugar', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Vanilla', 500000 , 'g');
insert into RawMaterials(name, amount , unit) values ('Wheat flour', 500000 , 'g');



insert into Customers (name, address) values ('Bjudkakor AB', 'Ystad');
insert into Customers (name, address) values ('Finkakor AB', 'Helsingborg');
insert into Customers (name, address) values ('Gästkakor AB', 'Hässleholm');
insert into Customers (name, address) values ('Kaffebröd AB', 'Landskrona');
insert into Customers (name, address) values ('Kalaskakor AB', 'Trelleborg');
insert into Customers (name, address) values ('Partykakor AB', 'Kristianstad');
insert into Customers (name, address) values ('Skånekakor AB', 'Perstorp');
insert into Customers (name, address) values ('Småbröd AB', 'Malmö');




insert into Recipes (cookie, raw_material , amount ) values ('Almond delight' ,'Butter' , 400 );
insert into Recipes (cookie, raw_material , amount ) values ('Almond delight' ,'Chopped almonds' , 279 );
insert into Recipes (cookie, raw_material , amount ) values ('Almond delight' ,'Cinnamon' , 10 );
insert into Recipes (cookie, raw_material , amount ) values ('Almond delight' ,'Flour' , 400 );
insert into Recipes (cookie, raw_material , amount ) values ('Almond delight' ,'Sugar' , 270 );

insert into Recipes (cookie, raw_material , amount ) values ('Amneris' ,'Butter' , 250 );
insert into Recipes (cookie, raw_material , amount ) values ('Amneris' ,'Eggs' , 250 );
insert into Recipes (cookie, raw_material , amount ) values ('Amneris' ,'Marzipan' , 750 );
insert into Recipes (cookie, raw_material , amount ) values ('Amneris' ,'Potato starch' , 25 );
insert into Recipes (cookie, raw_material , amount ) values ('Amneris' ,'Wheat flour' , 25 );

insert into Recipes (cookie, raw_material , amount ) values ('Berliner' ,'Butter' , 250 );
insert into Recipes (cookie, raw_material , amount ) values ('Berliner' ,'Chocolate' , 50 );
insert into Recipes (cookie, raw_material , amount ) values ('Berliner' ,'Eggs' , 50 );
insert into Recipes (cookie, raw_material , amount ) values ('Berliner' ,'Flour' , 350 );
insert into Recipes (cookie, raw_material , amount ) values ('Berliner' ,'Icing sugar' , 100 );
insert into Recipes (cookie, raw_material , amount ) values ('Berliner' ,'Vanilla sugar' , 5 );

insert into Recipes (cookie, raw_material , amount ) values ('Nut cookie' ,'Bread crumbs' , 125 );
insert into Recipes (cookie, raw_material , amount ) values ('Nut cookie' ,'Chocolate' , 50 );
insert into Recipes (cookie, raw_material , amount ) values ('Nut cookie' ,'Egg whites' , 350 );
insert into Recipes (cookie, raw_material , amount ) values ('Nut cookie' ,'Fine-ground nuts' , 750 );
insert into Recipes (cookie, raw_material , amount ) values ('Nut cookie' ,'Ground, roasted nuts ' , 625 );
insert into Recipes (cookie, raw_material , amount ) values ('Nut cookie' ,'Sugar' , 375 );

insert into Recipes (cookie, raw_material , amount ) values ('Nut ring' ,'Butter' , 450 );
insert into Recipes (cookie, raw_material , amount ) values ('Nut ring' ,'Flour' , 450 );
insert into Recipes (cookie, raw_material , amount ) values ('Nut ring' ,'Icing sugar' , 190 );
insert into Recipes (cookie, raw_material , amount ) values ('Nut ring' ,'Roasted, chopped nuts' , 225 );

insert into Recipes (cookie, raw_material , amount ) values ('Tango' ,'Butter' , 200 );
insert into Recipes (cookie, raw_material , amount ) values ('Tango' ,'Flour' , 300 );
insert into Recipes (cookie, raw_material , amount ) values ('Tango' ,'Sodium bicarbonate' , 4 );
insert into Recipes (cookie, raw_material , amount ) values ('Tango' ,'Sugar' , 250 );
insert into Recipes (cookie, raw_material , amount ) values ('Tango' ,'Vanilla' , 2 );
set foreign_key_checks = 1;
