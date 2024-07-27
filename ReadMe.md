# HoloMentor API

### Prerequisite

Create `env.properties` file in the root of the project

NOTE: The database configurations are required only if you are deploying the database locally

```env
## database
DB_USER=postgres
DB_PASSWORD=1234
DB_DATABASE=holomentor_db

## super admin
SUPER_ADMIN_FNAME=Super
SUPER_ADMIN_LNAME=Admin
SUPER_ADMIN_EMAIL=admin@holomentor.com
SUPER_ADMIN_PASSWORD=1234
```

# Naming Conventions
All branch descriptors should be in lowercase and separated by hyphens.

## Main Branch
* Convention: Named simply as "main".
* Description: The main branch contains production-ready code and serves as the primary branch for release deployments.

## Development Branch (DEV)
* Convention: Prefixed with "dev/" followed by a brief descriptor.
* Description: The development branch, labeled with the "DEV" prefix, is where ongoing work and feature development take place. It serves as the basis for creating feature branches.

## Feature Branches
* Convention: Prefixed with "feat/" followed by a brief descriptor.
* Description: Feature branches are created for implementing new features or enhancements in the project. They are based on the development branch.

## Bug Fix Branches
* Convention: Prefixed with "fix/" followed by a brief descriptor.
* Description: Bug-fix branches are used to address specific issues or bugs in the project. They are based on the development branch.

## Version Branches
* Convention: Prefixed with "vX/" followed by a brief descriptor.
* Description: Version branches are used to manage different versions of the project. They include main, stagging, and development branches for each version, facilitating version-specific development and maintenance.

## Example Branches
* Main: `main`
* Development: `dev`
* Feature Branch: `feat/new-feature`
* Bug Fix Branch: `fix/issue-fix`
* Version 1 Main: `v1/main`