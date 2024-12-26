    insert into warehouse (id, name, default_warehouse, isCustomsRegistered)
    values (1, 'DEFAULT', true, true);

    insert into user (id, firstName, lastName, email, roles, password, active)
    values (1, 'User', 'Admin', 'admin@bis.pt', 'ADMIN', '4EKiKKExWqmXTbTQGbchLw==:gm1CH9PEQOmXsAslrUOcMl/n/iGkpYTUvfM6F3fyAR0=', true);
    insert into user (id, firstName, lastName, email, roles, password, active)
    values (2, 'User', 'Read Only', 'read@bis.pt', 'READ_ONLY', '4EKiKKExWqmXTbTQGbchLw==:gm1CH9PEQOmXsAslrUOcMl/n/iGkpYTUvfM6F3fyAR0=', true);
    insert into user (id, firstName, lastName, email, roles, password, active)
    values (3, 'User', 'Generic', 'generic@bis.pt', 'GENERIC', '4EKiKKExWqmXTbTQGbchLw==:gm1CH9PEQOmXsAslrUOcMl/n/iGkpYTUvfM6F3fyAR0=', true);