package com.musicstore.integration;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ContainerExtender implements BeforeAllCallback {

  @Override
  public void beforeAll(ExtensionContext extensionContext) {
    ContainerCreator.mySqlContainer.start();
  }

  private void updateProperties() {
    System.setProperty("spring.datasource.url", ContainerCreator.mySqlContainer.getJdbcUrl());
    System.setProperty("spring.datasource.username", ContainerCreator.mySqlContainer.getUsername());
    System.setProperty("spring.datasource.password", ContainerCreator.mySqlContainer.getPassword());
    System.setProperty("spring.sql.init.mode", "never");
    System.setProperty("spring.jpa.hibernate.ddl-auto", "none");
    System.setProperty("spring.jpa.hibernate.use-new-id-generator-mappings", "false");
  }
}
