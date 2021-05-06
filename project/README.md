Cooking project
    - Burcu Hadimoglu 
    - Aleksandar Milicevic

How to run every container indipendently:
    - Postgres:
        - docker container run -d --name postgres -e PGDATA=/var/lib/postgresql/data/db-files/ -e POSTGRES_PASSWORD=admin -e POSTGRES_USER=admin -p 5432:5432 -v pgsql:/var/lib/postgresql/data/db-files/ postgres
        - docker container exec -it postgres bash
        - psql -U admin