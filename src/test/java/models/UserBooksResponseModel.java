package models;

import lombok.Data;

import java.util.List;

@Data
public class UserBooksResponseModel {
    String userId;
    String username;
    List<UserBooksRequestModel> books;
}
