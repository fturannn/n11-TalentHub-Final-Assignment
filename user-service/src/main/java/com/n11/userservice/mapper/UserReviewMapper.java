package com.n11.userservice.mapper;

import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.request.UserReviewSaveRequest;
import com.n11.userservice.request.UserReviewUpdateRequest;
import com.n11.userservice.request.UserUpdateRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserReviewMapper {

    UserReviewMapper INSTANCE = Mappers.getMapper(UserReviewMapper.class);

    @Mapping(source = "userId", target = "user.id")
    UserReview convertToUserReview(UserReviewSaveRequest request);

    @Mappings({
            @Mapping(source = "userReview.user.name", target = "userName"),
            @Mapping(source = "userReview.user.surname", target = "userSurname"),
            @Mapping(target = "userFullName", expression = "java(userName + \" \" + userSurname)"),
    })
    UserReviewDTO convertToUserReviewDTO(UserReview userReview);

    @Mappings({
            @Mapping(source = "userReview.user.name", target = "userName"),
            @Mapping(source = "userReview.user.surname", target = "userSurname"),
            @Mapping(target = "userFullName", expression = "java(userReviewDTO.getUserName() + \" \" + userReviewDTO.getUserSurname())"),
    })
    List<UserReviewDTO> convertToUserReviewDTOs(List<UserReview> userReviews);

    @Mapping(target = "id", ignore = true)
    void updateUserReviewFields(@MappingTarget UserReview userReview, UserReviewUpdateRequest request);

}
