package com.green.greengram4.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserInfoSelDto {
    private int targetIuser;
    @JsonIgnore
    private int loginedIuser;
}
