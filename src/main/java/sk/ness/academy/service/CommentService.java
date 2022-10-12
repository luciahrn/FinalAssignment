package sk.ness.academy.service;

import sk.ness.academy.domain.Comment;




public interface CommentService {


    void createArticleComment(Comment comment, Integer articleId);
    Comment getArticleComment(Integer commentId, Integer articleId);
    void deleteArticleComment(Integer commentId, Integer articleId);




}
