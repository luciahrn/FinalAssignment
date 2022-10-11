package sk.ness.academy.dao;

import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentDAO {

    /** Returns {@link Comment} with provided ID */
    Comment findByID(Integer commentId);

    /** Returns all available {@link Comment}s */
    List<Comment> findAll();

    /** Persists {@link Comment} into the DB */
    void persist(Comment comment);

    /** Deletes by id */
    void delete(Integer commentId);
//    void updateArticleComment(Comment comment,Integer articleId);
    void createArticleComment(Comment comment,Integer articleId);
    Comment getArticleComment(Integer commentId, Integer articleId);
    void deleteArticleComment(Integer commentId, Integer articleId);



}