package kr.ac.mjc.blog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.ac.mjc.blog.domain.Article;
import kr.ac.mjc.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BlogViewController {
    @Autowired
    BlogService blogService;

    @GetMapping("/articles")
    public ModelAndView getArticles(){
        ModelAndView mav=new ModelAndView();
        List<Article> articles=blogService.findAll();
        mav.addObject("articles", articles);
        mav.setViewName("articleList");
        return mav;
    }

    @GetMapping("/articles/{id}")
    public ModelAndView getArticle(@PathVariable long id){
        ModelAndView mav=new ModelAndView();
        Article article=blogService.findOne(id);
        mav.addObject("article",article);
        mav.setViewName("article");
        return mav;
    }
    @GetMapping("/new-article")
    public String createArticle(HttpServletRequest httpServletRequest){
        HttpSession session=httpServletRequest.getSession(true);

        String userId=(String)session.getAttribute("userId");
        if(userId==null){   //로그인 되있지 않았을때
            return "login";
        }
        return "newArticle";
    }

    @GetMapping("/articles/modify/{id}")
    public ModelAndView modifyArticle(@PathVariable long id){
        ModelAndView mav=new ModelAndView();
        Article article=blogService.findOne(id);
        mav.addObject("article",article);
        mav.setViewName("articleModify");
        return mav;
    }

}
