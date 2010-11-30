import java.util.List;

import hu.bme.viaum105.Util;
import hu.bme.viaum105.data.nonpersistent.Role;
import hu.bme.viaum105.data.persistent.Actor;
import hu.bme.viaum105.data.persistent.Comment;
import hu.bme.viaum105.data.persistent.Episode;
import hu.bme.viaum105.data.persistent.Series;
import hu.bme.viaum105.data.persistent.User;
import hu.bme.viaum105.service.DaoException;
import hu.bme.viaum105.service.SeriesPortal;
import hu.bme.viaum105.service.ServerException;
import hu.bme.viaum105.web.server.ServiceLocator;

public class EjbTest {

    public static void main(String[] args) throws Exception {
	new EjbTest(ServiceLocator.getInstance().getSeriesPortalService()).registerUser("admin");
    }

    private final SeriesPortal seriesPortalService;

    public EjbTest(SeriesPortal seriesPortalService) {
	this.seriesPortalService = seriesPortalService;
    }

    private void listSeries(List<Series> listSeriesPaged) throws DaoException {
	for (Series s : listSeriesPaged) {
	    System.out.println(s + //
		    " " + this.seriesPortalService.getRate(s.getId()) + //
		    " (" + this.seriesPortalService.getLikeCount(s.getId()) + " likes)");
	    for (Episode e : s.getEpisodes()) {
		System.out.println("");
		System.out.println(e + //
			" " + this.seriesPortalService.getRate(e.getId()) + //
			" (" + this.seriesPortalService.getLikeCount(e.getId()) + " likes)");
		System.out.print("Actors: ");
		for (Actor a : e.getActors()) {
		    System.out.print(a + " ");
		}
		System.out.println("");
		System.out.println("Comments:");
		for (Comment c : e.getComments()) {
		    System.out.println(c);
		}
	    }

	    System.out.println("");
	    System.out.println("Comments:");
	    for (Comment c : s.getComments()) {
		System.out.println(c);
	    }
	}
    }

    private void makeSeries(String title) throws DaoException {
	Series s = new Series();
	s.setDirector("Stephen Spielberg");
	s.setTitle(title);
	s.setDescription(title + " description");
	s.setImdbUrl("www.imdb.com/" + title.replace(" ", "_"));
	s = this.seriesPortalService.save(s);

	for (int season = 1; season < 7; season++) {
	    for (int ep = 1; ep < 13; ep++) {
		Episode e = new Episode();
		e.setSeries(s);
		s.getEpisodes().add(e);
		e.setTitle(title + " s" + season + "e" + ep);
		e.setSeasonNumber(season);
		e.setEpisodeNumber(ep);
		e.setDescription("Great episode s" + season + "e" + ep);

		e.addActor("Sylvester Stallone");
		e.addActor("Bruce Willis");
		e.addActor("Matt Le Blanc");
		e.addActor("David Schwimmer");
		e.addActor("Jennifer Aniston");

		this.seriesPortalService.save(e);
	    }
	}
    }

    private User registerUser(String loginname) throws DaoException, ServerException {
	User user = new User();
	user.setDisplayName("Teszt felhasználó");
	user.setEmail("test@test.hu");
	user.setLoginName(loginname);
	user.setPassword(Util.md5Hash("test"));
	user.setRole(Role.ADMIN);
	return this.seriesPortalService.register(user);
    }

    private void run() throws Exception {
	System.out.println("Run");
	// this.registerUser("test");
	User user = this.seriesPortalService.login("test", Util.md5Hash("test"));
	System.out.println("logged in");
	List<Series> series = this.seriesPortalService.listSeriesPaged(20, 0);
	this.listSeries(series);
	for (Series s : series) {
	    this.seriesPortalService.comment(s, user, "This series was reviewed by " + user);
	    this.seriesPortalService.rate(s, user, 3);
	    for (Episode e : s.getEpisodes()) {
		this.seriesPortalService.comment(e, user, "This episode was reviewed");
		this.seriesPortalService.like(e, user);
	    }
	}
	System.out.println("done");
    }

}
