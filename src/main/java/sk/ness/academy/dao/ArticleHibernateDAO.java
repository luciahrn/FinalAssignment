package sk.ness.academy.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
    System.err.println("som tu");
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

  @Override
  public void ingestArticles(String jsonArticles) {
    JSONParser parser = new JSONParser();
    Session session = this.sessionFactory.getCurrentSession();

    try {
      // Parse JSON string using JSON parser.
      Object object = parser.parse(jsonArticles);
      JSONArray array = (JSONArray) object;
      System.out.println("First object:");
      System.out.println(array.get(0));
      // Get JSON object from JSON array.
      JSONObject jsonObject = (JSONObject) array.get(1);
      System.out.println("Second object:");
      System.out.println("Name:" + jsonObject.get("author"));

      for (int i=0;i<array.size();i++) {
        JSONObject jsonObj = (JSONObject) array.get(i);


        Article a = new Article();
        a.setAuthor((String) jsonObj.get("author"));
        a.setText((String) jsonObj.get("text"));
        a.setTitle((String) jsonObj.get("title"));
        session.save(a);



      }


    } catch (org.json.simple.parser.ParseException e) {
      e.printStackTrace();
    }
  }


}
