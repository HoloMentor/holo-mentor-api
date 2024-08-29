#!/bin/bash
# Get the current timestamp for versioning
timestamp=$(date +"%Y%m%d%H%M%S")
# Read the description from the command line
description="$1"
# Replace spaces with underscores and convert to lowercase
description=$(echo "$description" | tr " " "_" | tr "[:upper:]" "[:lower:]")
# Create the file name
filename="V${timestamp}__${description}.sql"
# Create the migration file
touch "src/main/resources/db/migration/${filename}"
# Output the file name
echo "Created migration: src/main/resources/db/migration/${filename}"
