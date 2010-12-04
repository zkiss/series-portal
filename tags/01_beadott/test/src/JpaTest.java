import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hu.bme.viaum105.data.persistent.Episode;
import hu.bme.viaum105.data.persistent.Series;
import hu.bme.viaum105.data.persistent.Subtitle;
import hu.bme.viaum105.data.persistent.SubtitleData;
import hu.bme.viaum105.ejb.SeriesPortalDao;
import hu.bme.viaum105.web.server.converter.Converter;
import hu.bme.viaum105.web.shared.dto.persistent.ActorDto;
import hu.bme.viaum105.web.shared.dto.persistent.EpisodeDto;

public class JpaTest {

    public static void main(String[] args) throws Exception {
	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SERIESPORTAL");
	new JpaTest(entityManagerFactory).testSearch();
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
	s.setDirector("Stephen Spielberg");
	s.setDescription("Teszt sorozat leírása");
	s.setImdbUrl("http://www.imdb.com");
	s.setTitle("Teszt %");

	s.addActor("Sorozat színész");

	s = dao.save(s);

	Episode e = new Episode();
	e.setSeries(s);
	e.setAirDate(new Date());
	e.setDescription("Teszt epizód leírása");
	e.setEpisodeNumber(1);
	e.setSeasonNumber(1);
	e.setTitle("Epizód 1");
	e.addActor("Első színész");
	e.addActor("Második Szereplő");

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

    private void testLazy1() {
	EntityManager entityManager = this.entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	Series s = new Series();
	s.setDescription("test");
	s.setImdbUrl("test");
	s.setTitle("test");
	s = entityManager.merge(s);

	Episode e = new Episode();
	e.setAirDate(new Date());
	e.setDescription("e");
	e.setEpisodeNumber(1);
	e.setSeasonNumber(1);
	e.setSeries(s);
	e.setTitle("e");
	e = entityManager.merge(e);

	Subtitle st = new Subtitle();
	st.setAddedAt(new Date());
	st.setEpisode(e);
	st.setFileName("sub.sub");
	SubtitleData sd = new SubtitleData();
	sd.setSubtitle(st);
	sd.setFileData(new byte[] { (byte) 255, (byte) 65 });
	st.setSubtitleData(sd);
	st = entityManager.merge(st);

	entityManager.getTransaction().commit();
    }

    private void testLazy2() throws Exception {
	EntityManager em = this.entityManagerFactory.createEntityManager();
	Episode e = (Episode) em.createQuery("select e from Episode e where e.id = :id").setParameter("id", 2l).getSingleResult();
	em.close();
	e.toString();
	System.out.println(Converter.convert(e));
    }

    private void testSearch() throws Exception {
	EntityManager em = this.entityManagerFactory.createEntityManager();
	TreeSet<String> actors = new TreeSet<String>();
	actors.add("Első színész");
	actors.add("Els? színész");
	SeriesPortalDao dao = new SeriesPortalDao(em);
	dao.listTopRatedSeries(10, 0);
	System.out.println(dao.listUnapprovedComments(100, 0));
    }

}
