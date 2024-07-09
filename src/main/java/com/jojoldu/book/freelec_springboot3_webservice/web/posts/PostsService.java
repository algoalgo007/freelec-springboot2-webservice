package com.jojoldu.book.freelec_springboot3_webservice.web.posts;

import com.jojoldu.book.freelec_springboot3_webservice.web.domain.posts.Posts;
import com.jojoldu.book.freelec_springboot3_webservice.web.domain.posts.PostsRepository;
import com.jojoldu.book.freelec_springboot3_webservice.web.dto.PostsListResponseDto;
import com.jojoldu.book.freelec_springboot3_webservice.web.dto.PostsResponseDto;
import com.jojoldu.book.freelec_springboot3_webservice.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.freelec_springboot3_webservice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts findPost = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 ID 입니다." + id));

        findPost.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts findPost = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 ID 입니다." + id));

        return new PostsResponseDto(findPost);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않은 ID 입니다." + id));
        postsRepository.delete(posts);
    }
}
