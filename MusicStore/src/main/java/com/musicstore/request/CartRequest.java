package com.musicstore.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CartRequest {
  private int productId;
  private int quantity;
  @Email @NotBlank
  private String mail;
}
