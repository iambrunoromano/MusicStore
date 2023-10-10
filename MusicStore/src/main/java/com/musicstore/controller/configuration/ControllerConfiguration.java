package com.musicstore.controller.configuration;

import com.musicstore.converter.UserRequestConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ControllerConfiguration implements WebMvcConfigurer {

  private final UserRequestConverter userRequestConverter;

  @Autowired
  public ControllerConfiguration(UserRequestConverter userRequestConverter) {
    this.userRequestConverter = userRequestConverter;
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(userRequestConverter);
  }
}
