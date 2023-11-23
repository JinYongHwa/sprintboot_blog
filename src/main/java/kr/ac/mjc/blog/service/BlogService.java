package kr.ac.mjc.blog.service;

import kr.ac.mjc.blog.domain.Article;
import kr.ac.mjc.blog.domain.User;
import kr.ac.mjc.blog.dto.AddArticleRequest;
import kr.ac.mjc.blog.dto.UpdateArticleRequest;
import kr.ac.mjc.blog.dto.UserResponse;
import kr.ac.mjc.blog.repository.BlogRepository;
import kr.ac.mjc.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    public Article save(AddArticleRequest request,String userId){
        Article article=request.toEntity();
        User user=userRepository.findByEmail(userId);
        article.setWriter(user);
        return blogRepository.save(article);
    }

    public List<Article> findAll(){
        List<Article> articles=blogRepository.findAll();
        return articles;
    }

    public Article findOne(long id){
        Article article=blogRepository.findById(id).orElseThrow();
        return article;
    }

    public void delete(long id){
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request){
        Article article=blogRepository.findById(id).orElseThrow();
        article.update(request.getTitle(),request.getContent());
        return article;
    }
}
