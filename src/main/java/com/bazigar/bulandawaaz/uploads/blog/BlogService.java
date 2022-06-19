package com.bazigar.bulandawaaz.uploads.blog;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import com.bazigar.bulandawaaz.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    public Response uploadBlog(Long userId, String content, String location) {
        if (userId == null || content == null) {
            return new Response(
                    "userId, content and location are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Blog blog = new Blog(
                userId,
                content,
                location
        );
        blogRepository.save(blog);
        return new Response(
                "Blog created successfully!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                blog.getId()
        );
    }

    public Response getAllBlogs(Long userId) {
        if (userId == null) {
            return new Response(
                    "userId is required and cannot be null!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given id",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        List<Blog> blogs = blogRepository.findAll();
        return new Response(
                blogs.size()+" blogs found!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                blogs
        );
    }
}