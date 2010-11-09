import java.util.Date;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hu.bme.viaum105.data.persistent.Episode;
import hu.bme.viaum105.data.persistent.Series;
import hu.bme.viaum105.ejb.SeriesPortalDao;
import hu.bme.viaum105.web.server.converter.Converter;
import hu.bme.viaum105.web.shared.dto.persistent.ActorDto;
import hu.bme.viaum105.web.shared.dto.persistent.EpisodeDto;

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

	e.addActor("Első szereplő");
	e.addActor("Második szereplő");
	e.addLabel("vicces");

	s.getEpisodes().add(e);

	dao.save(e);

	entityManager.getTransaction().commit();
	System.out.println("done");

    }

    private void testCollection() throws Exception {
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	Episode ep = (Episode) entityManager.createQuery("select e from Episode e where e.id = :id"). //
		setParameter("id", 5l).getSingleResult();
	entityManager.close();

	EpisodeDto ep1 = Converter.convert(ep);
	for (Iterator<ActorDto> iterator = ep1.getActors().iterator(); iterator.hasNext();) {
	    ActorDto a = iterator.next();
	    if (a.getName().equals("Sylvester Stallone")) {
		System.out.println("Actor removed: " + a);
		iterator.remove();
	    }
	}
	ep = Converter.convert(ep1);

	entityManager = this.entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	SeriesPortalDao dao = new SeriesPortalDao(entityManager);
	dao.save(ep);
	entityManager.getTransaction().commit();
	System.out.println("done");
    }

}
