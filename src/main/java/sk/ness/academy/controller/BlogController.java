package sk.ness.academy.controller;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;
import sk.ness.academy.service.CommentService;

@RestController
public class BlogController {
    public static final String EMPTY_RESPONSE = "{}";

    @Resource
    private ArticleService articleService;

    @Resource
    private AuthorService authorService;

    @Resource
    private CommentService commentService;

    // ~~ Article
    @RequestMapping(value = "articles", method = RequestMethod.GET)
    public List<Article> getAllArticles() {
        try {
            return this.articleService.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Articles could not be found");

        }

    }

    @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
    public Article getArticle(@PathVariable final Integer articleId) {
        try {
            return this.articleService.findByID(articleId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article could not be found");

        }
    }


    @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
    public List<Article> searchArticle(@PathVariable final String searchText) {
        try {
            return this.articleService.searchText(searchText);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Articles could not be found");

        }
    }

    @RequestMapping(value = "articles", method = RequestMethod.PUT)
    public void addArticle(@RequestBody final Article article) {

        try {
            this.articleService.createArticle(article);
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Article could not be added");

        }
    }

    // ~~ Author
    @RequestMapping(value = "authors", method = RequestMethod.GET)
    public List<Author> getAllAuthors() {
        try {
            return this.authorService.findAll();
        }catch (Exception e ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article could not be found");

        }

    }

    @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
    public List<AuthorStats> authorStats() {
        try {
            return this.authorService.getAllAuthorsWithStats();
        }catch (Exception e ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Articles could not be found");

        }
    }


    //1. Introduce article delete functionality DELETE http://localhost:8080/articles/{articleId} where article with articleId is deleted from database
    @RequestMapping(value = "articles/{articleId}", method = RequestMethod.DELETE)
    public void deleteArticleById(@PathVariable("articleId") Integer articleId) {
        try {
            this.articleService.deleteArticleById(articleId);
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment could not be deleted");

        }
    }


    @RequestMapping(value = "articles/{articleId}", method = RequestMethod.PUT)
    public void createArticleComment(@RequestBody final Comment comment,
                                     @PathVariable("articleId") Integer articleId) {

        try {
            this.commentService.createArticleComment(comment, articleId);
        } catch (Exception e) {
            System.err.println("nhby" + e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment could not be added");

        }
    }

    @RequestMapping(value = "articles/{articleId}/comments/{commentId}", method = RequestMethod.GET)
    public ResponseEntity getArticleComment(@PathVariable("articleId") Integer
                                                    articleId, @PathVariable("commentId") Integer commentId) {

        if (this.commentService.getArticleComment(commentId, articleId) == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Article not found");
        } else {
            return new ResponseEntity<>(this.commentService.getArticleComment(commentId, articleId), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "articles/{articleId}/comments/{commentId}", method = RequestMethod.DELETE)
    public void deleteArticleComment(@PathVariable("articleId") Integer
                                             articleId, @PathVariable("commentId") Integer commentId) {

        try {
            this.commentService.deleteArticleComment(commentId, articleId);
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment could not be deleted");

        }
    }


}
