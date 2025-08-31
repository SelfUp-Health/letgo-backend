# Use the same base image as in your docker-compose.yml
FROM postgres:17.4-alpine

# Copy the restore script into the initialization directory
COPY restore-backup.sh /docker-entrypoint-initdb.d/restore-backup.sh

# Ensure the restore script is executable (though the entrypoint might handle this, it's good practice)
RUN chmod +x /docker-entrypoint-initdb.d/restore-backup.sh
