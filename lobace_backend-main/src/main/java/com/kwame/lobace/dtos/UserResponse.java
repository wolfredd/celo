package com.kwame.lobace.dtos;


import com.kwame.lobace.Models.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserResponse extends ResponseMessage{

    private User data;
}
