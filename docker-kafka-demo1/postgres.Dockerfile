FROM postgres:14

COPY --chown=app_user:app_user var/postgres_init_scripts/ /docker-entrypoint-initdb.d/
