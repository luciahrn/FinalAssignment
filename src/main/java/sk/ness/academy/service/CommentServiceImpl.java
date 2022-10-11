package sk.ness.academy.service;

import org.springframework.stereotype.Service;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Comment;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentDAO commentDAO;

//    @Override
//    public void updateArticleComment(Comment comment, Integer articleId) {
//        this.commentDAO.updateArticleComment(comment,articleId);
//    }

    @Override
    public void createArticleComment(Comment comment, Integer articleId) {
        this.commentDAO.createArticleComment(comment,articleId);
    }
    @Override
    public Comment getArticleComment(Integer commentId, Integer articleId) {
        return  this.commentDAO.getArticleComment(commentId, articleId);
    }

    @Override
    public void deleteArticleComment(Integer commentId, Integer articleId) {
        this.commentDAO.deleteArticleComment(commentId, articleId);

    }


}
