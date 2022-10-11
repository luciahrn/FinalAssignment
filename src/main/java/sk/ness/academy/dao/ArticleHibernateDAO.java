package sk.ness.academy.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.ArticleDto;
import sk.ness.academy.dto.AuthorStats;

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
//    return this.sessionFactory.getCurrentSession().createSQLQuery("select articles.title from articles").addEntity(Article.class).list();
        return this.sessionFactory.getCurrentSession().createSQLQuery("SELECT  a.id as id,a.title as title FROM articles a ")
                .addScalar("id", StandardBasicTypes.INTEGER)

                .addScalar("title", StringType.INSTANCE)

                .setResultTransformer(new AliasToBeanResultTransformer(ArticleDto.class)).list();
    }

    @Override
    public void persist(final Article article) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(article);
    }

    @Override
    public void deleteArticleById(Integer articleId) {
        Session session;
        session = this.sessionFactory.getCurrentSession();
        Article a = this.sessionFactory.getCurrentSession().load(Article.class, articleId);
        session.delete(a);
        session.flush();

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Article> searchText(String searchText) {
        return this.sessionFactory.getCurrentSession().createSQLQuery("select * from articles where title like '%" + searchText + "%' OR text like '%" + searchText + "%' OR author like '%" + searchText + "%'").addEntity(Article.class).list();

    }

    @Override
    public void ingestArticles(String jsonArticles) {
        JSONParser parser = new JSONParser();
        Session session = this.sessionFactory.getCurrentSession();

        try {
            Object object = parser.parse(jsonArticles);
            JSONArray array = (JSONArray) object;
            for (int i = 0; i < array.size(); i++) {
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
