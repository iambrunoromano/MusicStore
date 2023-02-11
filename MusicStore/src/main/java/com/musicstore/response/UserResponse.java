package com.musicstore.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
  private String mail;
  private String imgUrl;
}
