#!/bin/sh
set -e

# POSTGRES_USER and POSTGRES_DB are environment variables passed from docker-compose
# or docker run, and are available to this script.
# The main entrypoint script of the postgres image will have already created
# the database $POSTGRES_DB and set $POSTGRES_USER as its owner.

echo "Starting backup restoration from /tmp/backup.dump into database '$POSTGRES_DB' as user '$POSTGRES_USER'"

# pg_restore connects to the specified database and restores the data.
# -U: username
# -d: database name
# --verbose: provides detailed output
pg_restore -U "$POSTGRES_USER" -d "$POSTGRES_DB" --verbose /restore/backup.dump
rm /restore/backup.dump

echo "Backup restoration script completed."
