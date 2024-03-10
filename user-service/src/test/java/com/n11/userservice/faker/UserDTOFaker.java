package com.n11.userservice.faker;

import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.enums.EnumGender;
import com.n11.userservice.enums.EnumStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDTOFaker {

    public List<UserDTO> userDTOList() {
        UserDTO userDTO1 = new UserDTO(1L, "Test name 1", "Test surname 1"
                    , "Test country 1", "Test city 1", "Test district 1", 0);

        UserDTO userDTO2 = new UserDTO(2L, "Test name 2", "Test surname 2"
                , "Test country 2", "Test city 2", "Test district 2", 0);

        List<UserDTO> userDTOList = new ArrayList<>();
        userDTOList.add(userDTO1);
        userDTOList.add(userDTO2);

        return userDTOList;
    }

    public UserDTO userDTO() {
        UserDTO userDTO = new UserDTO(1L, "Test name", "Test surname"
                , "Test country", "Test city", "Test district", 0);
        return userDTO;
    }

}
