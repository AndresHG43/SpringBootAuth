CREATE TABLE "users" (
    "id" bigint UNIQUE PRIMARY KEY NOT NULL,
    "name" varchar(80) NOT NULL,
    "lastname" varchar(80) NOT NULL,
    "email" varchar(100) UNIQUE NOT NULL,
    "password" varchar(500) NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    "date_created" timestamp NOT NULL,
    "date_updated" timestamp,
    "date_deleted" timestamp
);
CREATE INDEX "user_index_0" ON "users" ("id");
CREATE SEQUENCE IF NOT EXISTS users_sequence
    AS bigint
    INCREMENT BY 1
    START WITH 2
    OWNED BY public.users.id;

CREATE TABLE "roles" (
    "id" bigint UNIQUE PRIMARY KEY NOT NULL,
    "name" varchar(25) NOT NULL,
    "description" varchar(255) NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    "date_created" timestamp NOT NULL,
    "date_updated" timestamp,
    "date_deleted" timestamp,
    "user_created" bigint NOT NULL,
    "user_updated" bigint,
    "user_deleted" bigint
);
ALTER TABLE "roles" ADD CONSTRAINT "fk_user_id_roles_created" FOREIGN KEY ("user_created") REFERENCES "users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "roles" ADD CONSTRAINT "fk_user_id_roles_updated" FOREIGN KEY ("user_updated") REFERENCES "users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "roles" ADD CONSTRAINT "fk_user_id_roles_deleted" FOREIGN KEY ("user_deleted") REFERENCES "users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
CREATE INDEX "roles_index_0" ON "roles" ("id");
CREATE SEQUENCE IF NOT EXISTS roles_sequence
    AS bigint
    INCREMENT BY 1
    START WITH 4
    OWNED BY public.roles.id;


CREATE TABLE "users_roles" (
    "id" bigint UNIQUE PRIMARY KEY NOT NULL,
    "user_id" bigint NOT NULL,
    "role_id" bigint NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    "date_created" timestamp NOT NULL,
    "date_updated" timestamp,
    "date_deleted" timestamp,
    "user_created" bigint NOT NULL,
    "user_updated" bigint,
    "user_deleted" bigint
);
ALTER TABLE "users_roles" ADD CONSTRAINT "fk_user_roles_user_id_user" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "users_roles" ADD CONSTRAINT "fk_user_roles_role_id_roles" FOREIGN KEY ("role_id") REFERENCES "roles" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "users_roles" ADD CONSTRAINT "fk_user_id_user_roles_created" FOREIGN KEY ("user_created") REFERENCES "users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "users_roles" ADD CONSTRAINT "fk_user_id_user_roles_updated" FOREIGN KEY ("user_updated") REFERENCES "users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "users_roles" ADD CONSTRAINT "fk_user_id_user_roles_deleted" FOREIGN KEY ("user_deleted") REFERENCES "users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
CREATE INDEX "user_roles_index_0" ON "users_roles" ("id");
CREATE SEQUENCE IF NOT EXISTS users_roles_sequence
    AS bigint
    INCREMENT BY 1
    START WITH 2
    OWNED BY public.users_roles.id;
