package com.ak.service;


import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ak.model.Article;
import com.ak.repository.ArticleRepository;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article saveArticle() {
        Article article = new Article(
                "Sample News Title",
                "This is a sample news description.",
                "http://example.com/sample-news",
                "http://example.com/sample-image.jpg",
                "Example Source",
                LocalDateTime.now()
        );
        return articleRepository.save(article);
    }
}
