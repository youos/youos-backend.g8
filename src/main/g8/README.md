# $projectname$

## Testing and Deployment

This project uses bitbucket-pipelines (see bitbucket-pipelines.yml file) to build
the project and the docker image. Using SSH the nginx configuration files and
the docker-compose file is uploaded to the destination instance and executed
from there.

### Running on Local Machine
- Test on your local machine using the `docker-compose.yml` file.
  - First build the .jar file: `sbt assembly`
  - `docker-compose up -d`
  - You can test correct startup by checking the logs `docker logs $name$_$name$_1` and by
    calling http://localhost:8080/meta/open-api
- If you need debugging and quicker restarts, stop the $name$ container (not the database)
and run `$package$.Main` directly from your IDE instead.
  - `docker-compose down`
  - `docker-compose up $projectname;format="normalize"$-db -d`
  - Rightclick `$package$.Main` in your IDE and click run/debug (alternatively: `sbt run`)
- When running the test cases (`sbt test`), ensure the database container is up and 
  running (`docker-compose up $projectname;format="normalize"$-db -d`).

### Running on Test/Staging Environment
Push to branch `master`.

### Running on Production Environment
Push to branch `$domain$`.