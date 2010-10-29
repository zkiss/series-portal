import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hu.bme.viaum105.data.persistent.Actor;
import hu.bme.viaum105.data.persistent.Episode;
import hu.bme.viaum105.data.persistent.Label;
import hu.bme.viaum105.data.persistent.Series;
import hu.bme.viaum105.ejb.SeriesPortalDao;

public class JpaTest {

    public static void main(String[] args) throws Exception {
	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SERIESPORTAL");
	new JpaTest(entityManagerFactory).run();
    }

    private final EntityManagerFactory entityManagerFactory;

    public JpaTest(EntityManagerFactory entityManagerFactory) {
	this.entityManagerFactory = entityManagerFactory;
    }

    private void run() throws Exception {
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	entityManager.toString();
	entityManager.getTransaction().begin();

	SeriesPortalDao dao = new SeriesPortalDao(entityManager);

	Series s = new Series();
	s.setDescription("Teszt sorozat leírása");
	s.setImdbUrl("http://www.imdb.com");
	s.setTitle("Teszt sorozat");

	s = dao.save(s);

	Episode e = new Episode();
	e.setSeries(s);
	e.setAirDate(new Date());
	e.setDescription("Teszt epizód leírása");
	e.setEpisodeNumber(1);
	e.setSeasonNumber(1);
	e.setTitle("Epizód 1");

	Actor a = new Actor();
	a.setName("Első szereplő");

	e.getActors().add(a);
	a = new Actor();
	a.setName("Második szereplő");
	e.getActors().add(a);

	s.getEpisodes().add(e);

	Label l = new Label();
	l.setLabel("vicces");
	e.getLabels().add(l);

	dao.save(e);

	entityManager.getTransaction().commit();
	System.out.println("done");

    }

}
