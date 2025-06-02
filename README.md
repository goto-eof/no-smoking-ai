# No Smoking

A set of applications that aim to record and predict, through Machine Learning, the number of cigarettes a user might
smoke.

![no-smoking-screenshot](image/no-smoking.png)

# First run

- build `no-smoking-java-common` by executing the following command `gradle publishToMavenLocal`. Gradle will create a
  jar file in the .m2 directory that will be used by
  `no-smoking-java-desktop` and `no-smoking-java-server` modules.
- run `sudo docker compose up -d` in order to create the containers defined
  in the `docker-compose.yml` files.

```bash
sudo docker compose -f docker-compose.db.yml up -d --build
sudo docker compose -f docker-compose.java-server.yml up -d --build
sudo docker compose -f no-smoking-python-server/docker-compose.python-server.yml up -d --build
```

- edit the `no-smoking-java-server/src/main/resources/application.yml` file which belongs to the
  `no-smoking-java-server` module.
- run `no-smoking-java-server` and `no-smoking-java-desktop` projects.

# Technologies/Tools

Java, Spring Boot, OpenFeign, hibernate, Spring JPA, Spring Security (OTP authentication), PostgreSQL, lombok, git,
python, swing, Lombok.

<img src="https://andre-i.eu/api/v1/ipResource/custom.png?host=https://github.com/goto-eof/no-smoking-ai" onerror="this.style.display='none'" />
