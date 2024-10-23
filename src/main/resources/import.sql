insert into unit (createddate, id, modifieddate, createdby, modifiedby, name) values (now(), 1, now(), 'script', 'script', 'Uni');

insert into supplier (isactive, createddate, id, modifieddate, nif, country, shortdescription, address, city, code, contactperson, contactpersonemail, contactpersonphone, createdby, currency, email, modifiedby, name, notes, phone, postalcode)
values (true, now(), 1 , now(), 999999999, 'Spain', '', '', '', 'RIC_MOLINA', '', '', '', 'script', 'EUR', '', 'script', 'Ricardo Molina', '', '', '');

insert into itemcategory (isactive, createddate, id, modifieddate, createdby, description, modifiedby, name)
values (true, now(), 1, now(), 'script', 'Malte, Trigo', 'script', 'Cereal');
insert into itemcategory (isactive, createddate, id, modifieddate, createdby, description, modifiedby, name)
values (true, now(), 2, now(), 'script', 'Garrafas 33CL', 'script', 'Garrafas');
insert into itemcategory (isactive, createddate, id, modifieddate, createdby, description, modifiedby, name)
values (true, now(), 3, now(), 'script', 'Barris 30L, 50L', 'script', 'Barris');

insert into item (deprecated, supplier_id, inventorytype, category_id, createddate, id, modifieddate, code, createdby, description, modifiedby, name, notes)
values (false, 1, 1, 1, now(), 1, now(), 'CEREAL_RM_PILS', 'script', '', 'script', 'MALTE PILSNER', '');
insert into item (deprecated, supplier_id, inventorytype, category_id, createddate, id, modifieddate, code, createdby, description, modifiedby, name, notes)
values (false, 1, 1, 1, now(), 2, now(), 'CEREAL_RM_MELAD', 'script', '', 'script', 'MALTE MELADONIN', '');