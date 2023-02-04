package com.musicstore.integration;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public class ContainerCreator {

  protected static final MySQLContainer mySqlContainer =
      new MySQLContainer(DockerImageName.parse("mysql:5.7.34"))
          .withUsername("root")
          .withPassword("root")
          .withDatabaseName("music_store");
}
