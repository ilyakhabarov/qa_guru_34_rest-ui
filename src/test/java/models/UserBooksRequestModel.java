package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserBooksRequestModel {
    String isbn;
    String title;
    String subTitle;
    String author;
    @JsonProperty("publish_date")
    String publishDate;
    String publisher;
    int pages;
    String description;
    String website;
}