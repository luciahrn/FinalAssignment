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
    public ResponseEntity getAllArticles() {


        if (this.articleService.findAll().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Articles not found");
        } else {
            return new ResponseEntity<>(this.articleService.findAll(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
    public ResponseEntity getArticle(@PathVariable final Integer articleId) {
        if (this.articleService.findByID(articleId) == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Article not found");
        } else {
            return new ResponseEntity<>(this.articleService.findByID(articleId), HttpStatus.OK);
        }


    }

    @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
    public List<Article> searchArticle(@PathVariable final String searchText) {
        return this.articleService.searchText(searchText);
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
        return this.authorService.findAll();
    }

    @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
    public List<AuthorStats> authorStats() {
        return this.authorService.getAllAuthorsWithStats();
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
    public void createArticleComment(@RequestBody final Comment comment, @PathVariable("articleId") Integer articleId) {

        try {
            this.commentService.createArticleComment(comment, articleId);
        } catch (Exception e) {
            System.err.println("nhby"+e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment could not be added");

        }
    }

    @RequestMapping(value = "articles/{articleId}/comments/{commentId}", method = RequestMethod.GET)
    public ResponseEntity getArticleComment(@PathVariable("articleId") Integer articleId, @PathVariable("commentId") Integer commentId) {

        if (this.commentService.getArticleComment(commentId, articleId) == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Article not found");
        } else {
            return new ResponseEntity<>(this.commentService.getArticleComment(commentId, articleId) , HttpStatus.OK);
        }
    }

    @RequestMapping(value = "articles/{articleId}/comments/{commentId}", method = RequestMethod.DELETE)
    public void deleteArticleComment(@PathVariable("articleId") Integer articleId, @PathVariable("commentId") Integer commentId) {

        try {
            this.commentService.deleteArticleComment(commentId, articleId);        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment could not be deleted");

        }
    }


}
