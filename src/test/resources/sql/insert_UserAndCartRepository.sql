insert into users (id, username, password, role, email, status)
values (1, 'Admin', '$2y$12$N4ANBfzs.4m54e4rXa5RxeGeIbHuK6jPoywj9SR5wH8POaxqt6Gxy', 'ROLE_ADMIN', 'German@mail.com',
        'ACTIVE'),
       (2, 'User', '$2y$12$6LEfdRapKUiv/w3/Mu2bYuyOFy5pnmx1pMDKjIrJdoO/57gP73iae', 'ROLE_USER',
        'User@mail.com', 'ACTIVE'),
       (3, 'James', '$2y$12$JOorBASfEFoVlKDiFL5xlO/QcPScprNKUWs5Dg.CR3T4RZU.g2Ji.', 'ROLE_USER', 'James@mail.com',
        'ACTIVE'),
       (4, 'Derrick', '$2y$12$CYvL2Kx4.Z21UJDJQIutV.jS8aoySh8GUlQqJfjdy858Px3RrOdSG', 'ROLE_USER', 'Derrick@mail.com',
        'BANNED');

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
       (5, 'car alarm', ' The best loud protection of your car');

insert into item_tags(item_ref, tag_ref)
values (1, 1),
       (2, 1),
       (2, 5),
       (3, 1),
       (3, 2),
       (3, 5),
       (4, 3),
       (5, 1),
       (5, 4);

insert into carts(id, user_ref)
values (1, 2),
       (2, 3),
       (3, 4);

insert into cart_items(cart_ref, item_ref)
values (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 2);