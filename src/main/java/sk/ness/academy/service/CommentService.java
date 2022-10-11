package sk.ness.academy.service;

import sk.ness.academy.domain.Comment;


import java.util.List;

public interface CommentService {

//    void updateArticleComment(Comment comment, Integer articleId);
    void createArticleComment(Comment comment, Integer articleId);
    Comment getArticleComment(Integer commentId, Integer articleId);
    void deleteArticleComment(Integer commentId, Integer articleId);




}
