package com.ak.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // Custom query methods can be added here if needed
}
