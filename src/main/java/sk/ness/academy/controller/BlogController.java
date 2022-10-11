package sk.ness.academy.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;
import sk.ness.academy.service.CommentService;

@RestController
public class BlogController {

  @Resource
  private ArticleService articleService;

  @Resource
  private AuthorService authorService;

  @Resource
  private CommentService commentService;

  // ~~ Article
  @RequestMapping(value = "articles", method = RequestMethod.GET)
  public List<Article> getAllArticles() {

      return this.articleService.findAll();
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
  public Article getArticle(@PathVariable final Integer articleId) {
	  return this.articleService.findByID(articleId);
  }

  @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
  public List<Article> searchArticle(@PathVariable final String searchText) {
    return this.articleService.searchText(searchText);
  }

  @RequestMapping(value = "articles", method = RequestMethod.PUT)
  public void addArticle(@RequestBody final Article article) {

      this.articleService.createArticle(article);
  }

  // ~~ Author
  @RequestMapping(value = "authors", method = RequestMethod.GET)
  public List<Author> getAllAuthors() {

      return this.authorService.findAll();
  }

  @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
  public List<AuthorStats> authorStats() {
	  return this.authorService.getAllAuthorsWithStats();
  }


  //1. Introduce article delete functionality DELETE http://localhost:8080/articles/{articleId} where article with articleId is deleted from database
  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.DELETE)
  public void deleteArticleById(@PathVariable("articleId") Integer articleId) {
      this.articleService.deleteArticleById(articleId);
  }
  //2.a create comment for existing article
  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.PUT)
  public void updateArticleComment(@RequestBody final Comment comment, @PathVariable("articleId") Integer articleId) {
      this.commentService.updateArticleComment(comment,articleId);
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.POST)
  public void createArticleComment(@RequestBody final Comment comment, @PathVariable("articleId") Integer articleId) {
    this.commentService.createArticleComment(comment,articleId);
  }

  @RequestMapping(value = "articles/{articleId}/comments/{commentId}", method = RequestMethod.GET)
  public Comment getArticleComment(@PathVariable("articleId") Integer articleId,@PathVariable("commentId") Integer commentId) {
    return this.commentService.getArticleComment(commentId,articleId);
  }

  @RequestMapping(value = "articles/{articleId}/comments/{commentId}", method = RequestMethod.DELETE)
  public void deleteArticleComment(@PathVariable("articleId") Integer articleId,@PathVariable("commentId") Integer commentId) {
     this.commentService.deleteArticleComment(commentId,articleId);
  }



}
