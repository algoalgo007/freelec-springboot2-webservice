package com.jojoldu.book.freelec_springboot3_webservice.web.dto;

import com.jojoldu.book.freelec_springboot3_webservice.web.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts posts) {
        id = posts.getId();
        title = posts.getTitle();
        content = posts.getContent();
        author = posts.getAuthor();
    }
}
