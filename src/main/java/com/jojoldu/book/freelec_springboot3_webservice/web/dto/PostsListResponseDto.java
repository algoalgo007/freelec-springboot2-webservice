package com.jojoldu.book.freelec_springboot3_webservice.web.dto;

import com.jojoldu.book.freelec_springboot3_webservice.web.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts posts) {
        id = posts.getId();
        title = posts.getTitle();
        author = posts.getAuthor();
        modifiedDate = posts.getModifiedDate();
    }
}
