package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.controller.contract.UserControllerContract;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.enums.EnumStatus;
import com.n11.userservice.errormessage.UserErrorMessage;
import com.n11.userservice.general.AppBusinessException;
import com.n11.userservice.mapper.UserMapper;
import com.n11.userservice.request.UserSaveRequest;
import com.n11.userservice.request.UserUpdateRequest;
import com.n11.userservice.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserControllerContractImpl implements UserControllerContract {

    private final UserEntityService userEntityService;

    @Override
    public UserDTO getById(Long id) {
        User user = userEntityService.findByIdWithControl(id);

        if(user.getStatus() == EnumStatus.PASSIVE) {
            throw new AppBusinessException(UserErrorMessage.USER_IS_NOT_ACTIVE);
        }

        return UserMapper.INSTANCE.convertToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> userList = userEntityService.findAll();

        List<User> activeUserList = userList.
                stream()
                .filter(user -> user.getStatus() == EnumStatus.ACTIVE).toList();

        return UserMapper.INSTANCE.convertToUserDTOs(activeUserList);
    }

    @Override
    public UserDTO save(UserSaveRequest request) {
        User user = UserMapper.INSTANCE.convertToUser(request);

        Optional<User> isUserExist = userEntityService.getUserByEmail(request.email());

        if(isUserExist.isPresent()) {
            throw new AppBusinessException(UserErrorMessage.USER_ALREADY_EXISTS);
        }

        user = userEntityService.save(user);

        return UserMapper.INSTANCE.convertToUserDTO(user);
    }

    @Override
    public UserDTO update(Long id, UserUpdateRequest request) {
        User user = userEntityService.findByIdWithControl(id);

        UserMapper.INSTANCE.updateUserFields(user, request);

        user = userEntityService.save(user);

        return UserMapper.INSTANCE.convertToUserDTO(user);
    }

    @Override
    public void delete(Long id) {
        User user = userEntityService.findByIdWithControl(id);

        userEntityService.delete(user);
    }
}
