package com.n11.userservice.dto;

public record UserDTO(Long id,
                      String name,
                      String surname,
                      String country,
                      String city,
                      String district,
                      String photoUrl,
                      int reviewCount) {
}
