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
  - Rightclick `$package$.Main` in your IDE and click run/debug. Then click stop. 
  - Go to Run/Debug Configurations in IntelliJ and configure your Main as follows:
    - Activate Share and Single instance only
    - Set Environment-variables to PORT=8080
  - You can now use this build-configuration to run/debug from IntelliJ
- When the database is running (see above first steps), you can also use `sbt run`
- When running the test cases (`sbt test`), ensure the database container is up and 
  running (`docker-compose up $projectname;format="normalize"$-db -d`).

### Using Bitbucket Pipelines for CI/CD
This template comes with preconfigured pipeline definition for bitbucket 
(see `bitbucket-pipelines.yml` file). 

#### Running on Test/Staging Environment
- Open the repository settings in bitbucket
- Ensure that `test.inspired.ag` is included in the list of known hosts
  in your bitbucket repository (Settings → Pipelines → SSH keys).
- Select `Generate new key` and add the public key to 
  `test.inspired ag:~/.ssh/authorized_keys` (add it as a new line). 
  At the end of the line for the public key, add the string ` $name$@bitbucket`
  in order to be able to identify the public key in the future when revoking access.
- Push to branch `master`.
- Select Pipelines in Bitbucket and enable it
- After your first pipeline for the backend ran successfully, you should be able to
  access it via http://$name$.test.inspired.ag/meta/open-api (ensure it shows the 
  correct host-name in the swagger-json)

#### Running on Production Environment
- See https://dynacrowd.atlassian.net/wiki/spaces/YODA/pages/486146065/Setting+up+an+AWS-Instance+for+production
- Finally, push to branch `$domain$`.

