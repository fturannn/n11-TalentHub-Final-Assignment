package com.n11.userservice.faker;

import com.n11.userservice.entity.User;
import com.n11.userservice.enums.EnumGender;
import com.n11.userservice.enums.EnumStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserFaker {

    public User user() {
        User user = new User();
        user.setId(1L);
        user.setName("Test name");
        user.setSurname("Test surname");
        user.setCountry("Test country");
        user.setCity("Test city");
        user.setDistrict("Test district");
        user.setLatitude(1);
        user.setLongitude(1);
        user.setBirthDate(LocalDate.of(1998, 11, 8));
        user.setEmail("testuser@email.com");
        user.setPhoneNumber("05555055050");
        user.setGender(EnumGender.MALE);
        user.setPhotoUrl("testphoto.png");
        user.setStatus(EnumStatus.ACTIVE);
        user.setReviewCount(0);
        user.setUserReviews(new ArrayList<>());
        return user;
    }

    public List<User> userList() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Test name 1");
        user1.setSurname("Test surname 1");
        user1.setCountry("Test country 1");
        user1.setCity("Test city 1");
        user1.setDistrict("Test district 1");
        user1.setLatitude(23);
        user1.setLongitude(23);
        user1.setBirthDate(LocalDate.of(1998, 11, 8));
        user1.setEmail("test1@email.com");
        user1.setPhoneNumber("05555055051");
        user1.setGender(EnumGender.MALE);
        user1.setPhotoUrl("testphoto1.png");
        user1.setStatus(EnumStatus.ACTIVE);
        user1.setReviewCount(0);
        user1.setUserReviews(new ArrayList<>());

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Test name 2");
        user2.setSurname("Test surname 2");
        user2.setCountry("Test country 2");
        user2.setCity("Test city 2");
        user2.setDistrict("Test district 2");
        user2.setLatitude(24);
        user2.setLongitude(24);
        user2.setBirthDate(LocalDate.of(1998, 11, 8));
        user2.setEmail("test2@email.com");
        user2.setPhoneNumber("05555055052");
        user2.setGender(EnumGender.FEMALE);
        user2.setPhotoUrl("testphoto2.png");
        user2.setStatus(EnumStatus.ACTIVE);
        user2.setReviewCount(0);
        user2.setUserReviews(new ArrayList<>());

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        return userList;
    }
}
