insert into tags (id, name)
values (1, 'electronics'),
       (2, 'computers'),
       (3, 'home appliances'),
       (4, 'auto'),
       (5, 'entertainments');

insert into items (id, name, description)
values (1, 'phone', 'The phone with a camera and buttons'),
       (2, 'TV', 'TV with a large CRT screen'),
       (3, 'laptop', 'The high-performance gaming laptop'),
       (4, 'vacuum cleaner', 'The best vacuum cleaner on the market'),
       (5, 'car alarm', 'The best loud protection of your car');

insert into item_tags(item_ref, tag_ref)
values (1, 1),
       (2, 5),
       (3, 1),
       (3, 5),
       (4, 3),
       (5, 1);




