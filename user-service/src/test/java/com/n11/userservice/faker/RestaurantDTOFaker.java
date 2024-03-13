package com.n11.userservice.faker;

import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.enums.EnumCuisineType;
import com.n11.userservice.enums.EnumFeatureType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDTOFaker {

    public List<RestaurantDTO> restaurantDTOList() {
        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            RestaurantDTO restaurantDTO = new RestaurantDTO();
            restaurantDTO.setId(String.valueOf(i));
            restaurantDTO.setName("Test Restaurant Name " + i);
            restaurantDTO.setPhoneNumber("0540404404" + i);
            restaurantDTO.setEmail("testrestaurant" + i + "@email.com");
            restaurantDTO.setCountry("Test Country " + i);
            restaurantDTO.setCity("Test City " + i);
            restaurantDTO.setDistrict("Test District " + i);
            restaurantDTO.setLatitude(1);
            restaurantDTO.setLongitude(1);
            restaurantDTO.setOpeningHour(LocalTime.of(10,0,0));
            restaurantDTO.setClosingHour(LocalTime.of(18,0,0));
            restaurantDTO.setCuisineType(EnumCuisineType.FRENCH);
            restaurantDTO.setFeatures(new ArrayList<>());
            restaurantDTO.setTotalReviewNumber(0L);
            restaurantDTO.setAverageRating(0);
            restaurantDTO.setDescription("Test Description " + i);
            restaurantDTOList.add(restaurantDTO);
        }

        return restaurantDTOList;
    }
}
