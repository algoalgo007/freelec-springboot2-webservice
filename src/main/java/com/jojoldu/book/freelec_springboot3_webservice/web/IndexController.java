package com.jojoldu.book.freelec_springboot3_webservice.web;

import com.jojoldu.book.freelec_springboot3_webservice.web.config.auth.dto.LoginUser;
import com.jojoldu.book.freelec_springboot3_webservice.web.config.auth.dto.SessionUser;
import com.jojoldu.book.freelec_springboot3_webservice.web.domain.user.User;
import com.jojoldu.book.freelec_springboot3_webservice.web.dto.PostsResponseDto;
import com.jojoldu.book.freelec_springboot3_webservice.web.posts.PostsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
