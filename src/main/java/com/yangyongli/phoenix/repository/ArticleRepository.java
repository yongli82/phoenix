package com.yangyongli.phoenix.repository;

import com.yangyongli.phoenix.domain.Article;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Article entity.
 */
@SuppressWarnings("unused")
public interface ArticleRepository extends JpaRepository<Article,Long> {

}
