package sk.ness.academy.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import sk.ness.academy.domain.Article;

@Repository
public class ArticleHibernateDAO implements ArticleDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @Override
  public Article findByID(final Integer articleId) {
    return (Article) this.sessionFactory.getCurrentSession().get(Article.class, articleId);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Article> findAll() {
    return this.sessionFactory.getCurrentSession().createSQLQuery("select * from articles").addEntity(Article.class).list();
  }

  @Override
  public void persist(final Article article) {
    this.sessionFactory.getCurrentSession().saveOrUpdate(article);
  }

  @Override
  public void deleteArticleById(Integer articleId) {
    Session session ;
    session=this.sessionFactory.getCurrentSession();
    Article a = this.sessionFactory.getCurrentSession().load(Article.class, articleId);
    session.delete(a);
    session.flush() ;

  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Article> searchText(String searchText) {


    return this.sessionFactory.getCurrentSession().createSQLQuery("select * from articles where title like '%" +searchText + "%' OR text like '%" +searchText + "%' OR author like '%'" +searchText + "'%'").addEntity(Article.class).list();

  }


}
