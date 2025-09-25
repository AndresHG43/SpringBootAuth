#!/bin/bash

# -- Allow connection to the database

# -- Allow schema use (required to access tables)

# -- Allow SELECT, INSERT, UPDATE, and DELETE permissions on all existing tables

# -- Make future tables inherit these permissions as well


# Exit if error
set -e

# Load variables from the .env file
set -o allexport
source .env
set +o allexport

# Execute SQL commands inside the Docker container
echo "Executing init userDB script"
docker exec -i postgre-auth-container psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d "$POSTGRES_DB" <<EOSQL
CREATE USER "$POSTGRES_READ_USER" WITH PASSWORD '$POSTGRES_READ_PASSWORD';

GRANT CONNECT ON DATABASE "$POSTGRES_DB" TO "$POSTGRES_READ_USER";

GRANT USAGE ON SCHEMA public TO "$POSTGRES_READ_USER";

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO "$POSTGRES_READ_USER";

ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO "$POSTGRES_READ_USER";
EOSQL
