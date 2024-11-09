package com.holomentor.holomentor.projections.user;

public interface UserProjection {
    String getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getRole();
    String getImage();
    String getCountry();
    String getCountryCode();
    String getContactNumber();
    Boolean getIsBlacklisted();
    Boolean getIsDeleted();
}