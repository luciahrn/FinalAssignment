package sk.ness.academy.dao;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import sk.ness.academy.domain.Article;

public interface ArticleDAO {

	  /** Returns {@link Article} with provided ID */
	  Article findByID(Integer articleId);

	  /** Returns all available {@link Article}s */
	  List<Article> findAll();

	  /** Persists {@link Article} into the DB */
	  void persist(Article article);

	/** Deletes Article by ArticleId from Database */
	  void deleteArticleById(Integer articleId);

	void updateArticleComment(Integer articleId);
	}
