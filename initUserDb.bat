@echo off
setlocal ENABLEDELAYEDEXPANSION

REM Load variables from .env
for /f "usebackq tokens=1,2 delims==" %%A in (".env") do (
    set "var=%%A"
    set "val=%%B"
    for /f "tokens=* delims= " %%x in ("!val!") do set "!var!=%%~x"
)

REM Create temporary SQL file
set "tmpfile=%cd%\grant_permissions.sql"
(
    echo CREATE USER "!POSTGRES_READ_USER!" WITH PASSWORD '!POSTGRES_READ_PASSWORD!';
    echo GRANT CONNECT ON DATABASE "!POSTGRES_DB!" TO "!POSTGRES_READ_USER!";
    echo GRANT USAGE ON SCHEMA public TO "!POSTGRES_READ_USER!";
    echo GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO "!POSTGRES_READ_USER!";
    echo ALTER DEFAULT PRIVILEGES IN SCHEMA public
    echo GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO "!POSTGRES_READ_USER!";
) > "!tmpfile!"

REM Run the SQL file in Docker
echo Executing init userDB script
docker exec -i postgre-auth-container psql -v ON_ERROR_STOP=1 -U !POSTGRES_USER! -d !POSTGRES_DB! < "!tmpfile!"

REM Delete temporary file
del "!tmpfile!"

endlocal
pause
exit
