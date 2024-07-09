package com.jojoldu.book.freelec_springboot3_webservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojoldu.book.freelec_springboot3_webservice.web.domain.posts.Posts;
import com.jojoldu.book.freelec_springboot3_webservice.web.domain.posts.PostsRepository;
import com.jojoldu.book.freelec_springboot3_webservice.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.freelec_springboot3_webservice.web.dto.PostsUpdateRequestDto;
import com.jojoldu.book.freelec_springboot3_webservice.web.posts.PostsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostsApiControllerTest {

    @Autowired
    PostsService postsService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    ObjectMapper objectMapper;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @WithMockUser(roles = "USER")
    @Test
    @DisplayName("posts_등록")
    void save() throws Exception {

        // given
        String title = "title";
        String content = "content";
        String author = "author";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String url = "http://localhost:8080/api/v1/posts";

        // when
        ResultActions perform = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then
        perform.andExpect(status().isOk());
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
        assertThat(all.get(0).getAuthor()).isEqualTo(author);
    }

    @WithMockUser(roles = "USER")
    @Test
    @DisplayName("posts_수정")
    void update() throws Exception {
        // given
        Posts savedPost = postsRepository.save(Posts.builder()
                .title("initial title")
                .content("initial content")
                .author("author")
                .build());

        Long updateId = savedPost.getId();
        String expectedTitle = "updated title";
        String expectedContent = "updated content";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:8080/api/v1/posts/" + updateId;

        // when
        ResultActions perform = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then
        perform.andExpect(status().isOk());
        Posts updatePost = postsRepository.findById(updateId).get();
        assertThat(updatePost.getTitle()).isEqualTo(expectedTitle);
        assertThat(updatePost.getContent()).isEqualTo(expectedContent);
    }

    @WithMockUser(roles = "USER")
    @Test
    @DisplayName("posts_조회")
    void getPosts() throws Exception {
        // given
        Posts savedPost = postsRepository.save(Posts.builder()
                .title("initial title")
                .content("initial content")
                .author("author")
                .build());

        Long id = savedPost.getId();

        String url = "http://localhost:8080/api/v1/posts/" + id;

        // when
        ResultActions perform = mockMvc.perform(get(url));

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("initial title"))
                .andExpect(jsonPath("$.content").value("initial content"))
                .andExpect(jsonPath("$.author").value("author"));
    }

    @Test
    @DisplayName("JPA_Auditing_테스트")
    public void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
        Posts savedPost = postsRepository.save(Posts.builder()
                .title("initial title")
                .content("initial content")
                .author("author")
                .build());

        // when
        List<Posts> all = postsRepository.findAll();

        // then
        Posts posts = all.get(0);

        System.out.println(">>>>>> createDate="+posts.getCreatedDate() + ", modifiedDate="+posts.getModifiedDate());
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}