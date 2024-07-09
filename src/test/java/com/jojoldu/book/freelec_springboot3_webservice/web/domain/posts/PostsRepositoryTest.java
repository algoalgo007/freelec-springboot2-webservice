package com.jojoldu.book.freelec_springboot3_webservice.web.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글을 저장한다")
    void save() {

        // given
        Posts posts = Posts.builder()
                .title("안녕하세요")
                .content("만나서 반갑습니다.")
                .author("신입개발자")
                .build();

        // when
        postsRepository.save(posts);

        // then
        List<Posts> savedPosts = postsRepository.findAll();
        assertThat(savedPosts.size()).isEqualTo(1);
        Posts findPosts = savedPosts.get(0);
        assertThat(findPosts.getTitle()).isEqualTo(posts.getTitle());
        assertThat(findPosts.getAuthor()).isEqualTo(posts.getAuthor());
        assertThat(findPosts.getContent()).isEqualTo(posts.getContent());
    }
}