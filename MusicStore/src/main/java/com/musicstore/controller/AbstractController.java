package com.musicstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicstore.request.UserRequest;

abstract public class AbstractController {

    protected UserRequest parseUserRequest(String userRequest) throws JsonProcessingException {
        // TODO: substitute json parsing with jwt auth
        return new ObjectMapper().readValue(userRequest, UserRequest.class);
    }
}
