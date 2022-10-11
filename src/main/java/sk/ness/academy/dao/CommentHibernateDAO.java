package sk.ness.academy.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hsqldb.HsqlException;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentHibernateDAO implements CommentDAO {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public Comment findByID(final Integer articleId) {
        return (Comment) this.sessionFactory.getCurrentSession().get(Comment.class, articleId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Comment> findAll() {
        return this.sessionFactory.getCurrentSession().createSQLQuery("select * from comments").addEntity(Article.class).list();
    }

    @Override
    public void persist(final Comment comment) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(comment);
    }

    @Override
    public void delete(Integer commentId) {

    }
//    @Override
//    public  void updateArticleComment(Comment comment,Integer articleId) {
//       Article a =this.sessionFactory.getCurrentSession().load(Article.class,articleId);
//        Transaction t=null;
//       if (a!=null) {
//           t=this.sessionFactory.getCurrentSession().getTransaction();
//           Comment c=this.sessionFactory.getCurrentSession().load(Comment.class,articleId);
//           c.setAuthor(comment.getAuthor());
//           c.setText(comment.getText());
//           c.setCreateTimestamp(c.getCreateTimestamp());
//           t.commit();
//       }
//    }

    @Override
    public  void createArticleComment(Comment comment,Integer articleId) {
        Article a =this.sessionFactory.getCurrentSession().load(Article.class,articleId);
        if (a!=null) {
            this.sessionFactory.getCurrentSession().saveOrUpdate(comment);
            List list=a.getComments();
            list.add(comment);
            a.setComments(list);
        }
    }
    @Override
    public Comment getArticleComment(Integer commentId, Integer articleId) {
        Article a = this.sessionFactory.getCurrentSession().load(Article.class, articleId);
        if (a != null) {
            List list=a.getComments();
            for (int i = 0; i < list.size(); i++) {
                Comment c=(Comment) list.get(i);
                if (c.getId()==commentId) {
                    return c;
                }
            }
        }
        return  (Comment) this.sessionFactory.getCurrentSession().load(Comment.class, commentId);
    }

    @Override
    public void deleteArticleComment(Integer commentId, Integer articleId) {
        Article a = this.sessionFactory.getCurrentSession().load(Article.class, articleId);
        if (a != null) {
            List list=a.getComments();
            for (int i = 0; i < list.size(); i++) {
                Comment c=(Comment) list.get(i);
                if (c.getId()==commentId) {
                    list.remove(c);
                    a.setComments(list);

                }
            }
        }

    }


}
