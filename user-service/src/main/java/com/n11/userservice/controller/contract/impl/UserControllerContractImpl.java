package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.client.RestaurantServiceClient;
import com.n11.userservice.controller.contract.UserControllerContract;
import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.enums.EnumStatus;
import com.n11.userservice.errormessage.UserErrorMessage;
import com.n11.userservice.general.AppBusinessException;
import com.n11.userservice.general.RabbitProducerService;
import com.n11.userservice.mapper.UserMapper;
import com.n11.userservice.request.UserSaveRequest;
import com.n11.userservice.request.UserUpdateRequest;
import com.n11.userservice.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserControllerContractImpl implements UserControllerContract {

    private final UserEntityService userEntityService;
    private final RestaurantServiceClient restaurantServiceClient;
    private final RabbitProducerService rabbitProducerService;

    @Override
    public UserDTO getById(Long id) {
        rabbitProducerService.sendInfoMessage("Getting user by ID: " + id);

        User user = userEntityService.findByIdWithControl(id);

        if(user.getStatus() == EnumStatus.PASSIVE) {
            throw new AppBusinessException(UserErrorMessage.USER_IS_NOT_ACTIVE);
        }

        rabbitProducerService.sendInfoMessage("Retrieved user: " + user.getName() + " " + user.getSurname());

        return UserMapper.INSTANCE.convertToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAll() {
        rabbitProducerService.sendInfoMessage("Getting all active users");

        List<User> userList = userEntityService.findAll();

        List<User> activeUserList = userList.
                stream()
                .filter(user -> user.getStatus() == EnumStatus.ACTIVE).toList();

        if(activeUserList.isEmpty()) {
            throw new AppBusinessException(UserErrorMessage.NO_ACTIVE_USERS);
        }

        rabbitProducerService.sendInfoMessage("Retrieved " + activeUserList.size() + " active users");

        return UserMapper.INSTANCE.convertToUserDTOs(activeUserList);
    }

    @Override
    public UserDTO save(UserSaveRequest request) {
        rabbitProducerService.sendInfoMessage("Saving new user: " + request.name() + " " + request.surname());

        User user = UserMapper.INSTANCE.convertToUser(request);

        Optional<User> isUserExist = userEntityService.getUserByEmail(request.email());

        if(isUserExist.isPresent()) {
            throw new AppBusinessException(UserErrorMessage.USER_ALREADY_EXISTS);
        }

        user = userEntityService.save(user);

        rabbitProducerService.sendInfoMessage("Saved new user: " + request.name() + " " + request.surname());

        return UserMapper.INSTANCE.convertToUserDTO(user);
    }

    @Override
    public UserDTO update(Long id, UserUpdateRequest request) {
        rabbitProducerService.sendInfoMessage("Updating user with ID: " + id);

        User user = userEntityService.findByIdWithControl(id);

        UserMapper.INSTANCE.updateUserFields(user, request);

        user = userEntityService.save(user);

        rabbitProducerService.sendInfoMessage("Updated user with ID: " + id);

        return UserMapper.INSTANCE.convertToUserDTO(user);
    }

    @Override
    public void delete(Long id) {
        rabbitProducerService.sendInfoMessage("Deleting user with ID: " + id);

        User user = userEntityService.findByIdWithControl(id);

        userEntityService.delete(user);

        rabbitProducerService.sendInfoMessage("Deleted user with ID: " + id);
    }

    @Override
    public UserDTO activate(Long id) {
        rabbitProducerService.sendInfoMessage("Activating user with ID: " + id);

        User user = userEntityService.findByIdWithControl(id);

        if(user.getStatus() == EnumStatus.ACTIVE) {
            throw new AppBusinessException(UserErrorMessage.USER_IS_ALREADY_ACTIVE);
        }

        user.setStatus(EnumStatus.ACTIVE);
        user = userEntityService.save(user);

        rabbitProducerService.sendInfoMessage("Activated user with ID: " + id);

        return UserMapper.INSTANCE.convertToUserDTO(user);
    }

    @Override
    public UserDTO deactivate(Long id) {
        rabbitProducerService.sendInfoMessage("Deactivating user with ID: " + id);

        User user = userEntityService.findByIdWithControl(id);

        if(user.getStatus() == EnumStatus.PASSIVE) {
            throw new AppBusinessException(UserErrorMessage.USER_IS_ALREADY_PASSIVE);
        }

        user.setStatus(EnumStatus.PASSIVE);
        user = userEntityService.save(user);

        rabbitProducerService.sendInfoMessage("Deactivated user with ID: " + id);

        return UserMapper.INSTANCE.convertToUserDTO(user);
    }

    @Override
    public List<RestaurantDTO> getRecommendedRestaurants(Long id) {
        rabbitProducerService.sendInfoMessage("Getting recommended restaurants for user with ID: " + id);

        User user = userEntityService.findByIdWithControl(id);
        List<RestaurantDTO> allRestaurants = restaurantServiceClient.getAllRestaurants().getBody().getData();
        HashMap<RestaurantDTO, Double> nearbyRestaurantsList = new HashMap<>();

        double userLatitude = user.getLatitude();
        double userLongitude = user.getLongitude();

        for(RestaurantDTO restaurant : allRestaurants) {
            double restaurantLatitude = restaurant.getLatitude();
            double restaurantLongitude = restaurant.getLongitude();

            double theta = userLongitude - restaurantLongitude;

            double distance = Math.round(1.609344 * (60 * 1.1515 * (180/Math.PI) * Math.acos(
                    Math.sin(userLatitude * (Math.PI/180)) * Math.sin(restaurantLatitude * (Math.PI/180)) +
                            Math.cos(userLatitude * (Math.PI/180)) * Math.cos(restaurantLatitude * (Math.PI/180)) * Math.cos(theta * (Math.PI/180)))));

            double recommendationPoint = (restaurant.getAverageRating() * 0.7) + (0.3 / distance);

            if(distance < 10) {
                nearbyRestaurantsList.put(restaurant, recommendationPoint);
            }
        }

        List<RestaurantDTO> restaurantsByRecommendationPoint = new ArrayList<>(nearbyRestaurantsList.keySet());

        if(restaurantsByRecommendationPoint.size() < 3) {
            rabbitProducerService.sendInfoMessage("Retrieved " + restaurantsByRecommendationPoint.size() + "recommended restaurants for user with ID: " + id);
            return restaurantsByRecommendationPoint;
        } else {
            rabbitProducerService.sendInfoMessage("Retrieved 3 recommended restaurants for user with ID: " + id);
            return restaurantsByRecommendationPoint.subList(0,3);
        }
    }
}
