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

    @Override
    public void updateArticleComment(Comment comment, Integer articleId) {
        this.commentDAO.updateArticleComment(comment,articleId);
    }
}
