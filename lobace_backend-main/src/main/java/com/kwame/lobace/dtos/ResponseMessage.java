package com.kwame.lobace.dtos;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseMessage {


    private String responseCode;
    private String responseMessage;
}
