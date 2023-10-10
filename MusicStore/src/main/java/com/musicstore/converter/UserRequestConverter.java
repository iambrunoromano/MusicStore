package com.musicstore.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicstore.request.UserRequest;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserRequestConverter implements Converter<String, UserRequest> {

  @SneakyThrows
  @Override
  public UserRequest convert(String source) {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(source, UserRequest.class);
  }
}
