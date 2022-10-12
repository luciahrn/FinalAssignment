package sk.ness.academy.dao;

import java.util.List;

import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;

@Repository
public class AuthorHibernateDAO implements AuthorDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @SuppressWarnings("unchecked")
  @Override
  public List<Author> findAll() {
    return this.sessionFactory.getCurrentSession().createSQLQuery("select distinct a.author as name from articles a ")
        .addScalar("name", StringType.INSTANCE)
        .setResultTransformer(new AliasToBeanResultTransformer(Author.class)).list();
  }
  @SuppressWarnings("unchecked")
  @Override
  public List<AuthorStats> findAllWithStats() {

    return this.sessionFactory.getCurrentSession().createSQLQuery("SELECT  a.author as authorName, COUNT(*) as articleCount FROM articles a GROUP BY authorName")
            .addScalar("authorName", StringType.INSTANCE)
            .addScalar("articleCount", StandardBasicTypes.INTEGER)
            .setResultTransformer(new AliasToBeanResultTransformer(AuthorStats.class)).list();
  }

}

