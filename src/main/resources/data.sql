insert into NODES(id, name, node_type ) values(1, 'A', 'ENTRY')
insert into NODES(id, name, node_type ) values(2, 'B', 'REGULAR')
insert into NODES(id, name, node_type ) values(3, 'C', 'REGULAR')
insert into NODES(id, name, node_type ) values(4, 'D', 'REGULAR')
insert into NODES(id, name, node_type ) values(5, 'E', 'REGULAR')
insert into NODES(id, name, node_type ) values(6, 'F', 'REGULAR')
insert into NODES(id, name, node_type ) values(7, 'G', 'REGULAR')
insert into NODES(id, name, node_type ) values(8, 'H', 'EXIT')


insert into CONNECTIONS(ID, FROM_NODE_ID, TO_NODE_ID, VALUE) values (1, 1,2,10)
insert into CONNECTIONS(ID, FROM_NODE_ID, TO_NODE_ID, VALUE) values (2, 1,3,5)
insert into CONNECTIONS(ID, FROM_NODE_ID, TO_NODE_ID, VALUE) values (3, 1,5,9)
insert into CONNECTIONS(ID, FROM_NODE_ID, TO_NODE_ID, VALUE) values (4, 2,4,8)
insert into CONNECTIONS(ID, FROM_NODE_ID, TO_NODE_ID, VALUE) values (5, 2,6,4)
insert into CONNECTIONS(ID, FROM_NODE_ID, TO_NODE_ID, VALUE) values (6, 3,5,3)
insert into CONNECTIONS(ID, FROM_NODE_ID, TO_NODE_ID, VALUE) values (7, 4,7,2)
insert into CONNECTIONS(ID, FROM_NODE_ID, TO_NODE_ID, VALUE) values (8, 5,8,19)
insert into CONNECTIONS(ID, FROM_NODE_ID, TO_NODE_ID, VALUE) values (9, 6,8,9)
insert into CONNECTIONS(ID, FROM_NODE_ID, TO_NODE_ID, VALUE) values (10, 6,7,8)
insert into CONNECTIONS(ID, FROM_NODE_ID, TO_NODE_ID, VALUE) values (11, 6,4,3)
insert into CONNECTIONS(ID, FROM_NODE_ID, TO_NODE_ID, VALUE) values (12, 7,8,7)


