INSERT INTO users(id, name, lastname, email, password)
VALUES (1, 'name', 'lastname', 'user@mail.com', 'cryptedPassword'); -- Use generate-password in AuthController

INSERT INTO roles(id, name, description, user_created)
VALUES (1, 'ROOT', 'Usuario super usuario', 1),
        (2, 'ADMIN', 'Usuario administrador', 1),
        (3, 'USER', 'Usuario nomal', 1);

INSERT INTO users_roles(id, user_id, role_id, user_created)
VALUES (1, 1, 1, 1)
