package com.n11.userservice.controller.contract;

import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.request.UserSaveRequest;
import com.n11.userservice.request.UserUpdateRequest;

import java.util.List;

public interface UserControllerContract {

    UserDTO getById(Long id);
    List<UserDTO> getAll();
    UserDTO save(UserSaveRequest request);
    UserDTO update(Long id, UserUpdateRequest request);
    void delete(Long id);
    UserDTO activate(Long id);
    UserDTO deactivate(Long id);
}
