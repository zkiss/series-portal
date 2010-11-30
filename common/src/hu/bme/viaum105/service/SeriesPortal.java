package hu.bme.viaum105.service;

import java.util.List;
import java.util.Set;

import hu.bme.viaum105.Util;
import hu.bme.viaum105.data.persistent.Comment;
import hu.bme.viaum105.data.persistent.Episode;
import hu.bme.viaum105.data.persistent.Like;
import hu.bme.viaum105.data.persistent.Rate;
import hu.bme.viaum105.data.persistent.RegisteredEntity;
import hu.bme.viaum105.data.persistent.Series;
import hu.bme.viaum105.data.persistent.Subtitle;
import hu.bme.viaum105.data.persistent.SubtitleData;
import hu.bme.viaum105.data.persistent.User;

public interface SeriesPortal {

    /**
     * A JNDI neve az EJB-nek
     */
    public final static String JNDI_NAME = "ejb/SeriesPortal";

    /**
     * Komment engedélyezése
     * 
     * @param commentId
     *            a komment ID-je
     * @throws DaoException
     *             adatbázis hiba
     * @throws ServerException
     *             ha nincs ilyen id-jű komment
     */
    public void approveComment(long commentId) throws DaoException, ServerException;

    /**
     * Felhasználó jelszavának megváltoztatása
     * 
     * @param userId
     * @param newPassword
     * @throws DaoException
     * @throws ServerException
     */
    public void changeUserPassword(long userId, String newPassword) throws DaoException, ServerException;

    /**
     * Megjegyzés hozzáadása
     * 
     * @param registeredEntity
     * @param user
     * @param comment
     * @return a hozzáadott megjegyzés
     * @throws DaoException
     */
    public Comment comment(RegisteredEntity registeredEntity, User user, String comment) throws DaoException;

    /**
     * Felirat letöltése
     * 
     * @param subtitleId
     * @return
     * @throws DaoException
     * @throws ServerException
     */
    public SubtitleData downloadSubtitle(long subtitleId) throws DaoException, ServerException;

    /**
     * Megmondja, hányan lájkolták már az adott ID-jű entitást
     * 
     * @param registeredEntityId
     * @return
     * @throws DaoException
     */
    public long getLikeCount(long registeredEntityId) throws DaoException;

    /**
     * Megmondja az összesített értékelését adott ID-jű entitásnak
     * 
     * @param registeredEntityId
     * @return az összesített értékelés, vagy <code>null</code>, ha még nem volt
     *         értékelve
     * @throws DaoException
     */
    public Double getRate(long registeredEntityId) throws DaoException;

    /**
     * Létezik-e már adott felhasználónévvel user
     * 
     * @param loginName
     * @return
     * @throws DaoException
     */
    public boolean isLoginNameAvailable(String loginName) throws DaoException;;

    /**
     * Lájkolás
     * 
     * @param registeredEntity
     * @param user
     * @return
     * @throws DaoException
     */
    public Like like(RegisteredEntity registeredEntity, User user) throws DaoException;

    /**
     * Visszaadja az összes sorozatot név szerint rendezve. A lekérdezés
     * oldalakra van tördelve, minden oldalt külön kell lekérdezni.
     * 
     * @param pageSize
     *            maximum hány elem legyen a visszaadott listában
     * @param pageNumber
     *            hányadik oldalt kérdezzük le. A számozás 0-tól indul
     * @return
     * @throws DaoException
     * @throws ServerException
     */
    public List<Series> listSeriesPaged(int pageSize, int pageNumber) throws DaoException;

    /**
     * A legjobbra értékelt sorozatokat listázza ki. Ha egy sorozat még nem volt
     * értékelve, a listában sem lesz benne.
     * 
     * @param pageSize
     *            maximum hány elem legyen a visszaadott listában
     * @param pageNumber
     *            hányadik oldalt kérdezzük le. A számozás 0-tól indul
     * @return
     * @throws DaoException
     */
    public List<Series> listTopRatedSeries(int pageSize, int pageNumber) throws DaoException;

    /**
     * Bejelentkezés. Ha sikeres, visszatér a felhasználóval, ha sikertelen,
     * exception-t dob
     * 
     * @param loginname
     * @param passwordHash
     *            a jelszó hash-e. Hasheléshez használjuk a
     *            {@link Util#md5Hash(String)} metódust!
     * @return
     * @throws DaoException
     * @throws ServerException
     *             sikertelen bejelentkezés esetén
     */
    public User login(String loginname, String passwordHash) throws DaoException, ServerException;

    /**
     * Értékelés
     * 
     * @param registeredEntity
     * @param user
     * @param rate
     * @throws DaoException
     */
    public Rate rate(RegisteredEntity registeredEntity, User user, int rate) throws DaoException;

    /**
     * Új felhasználó regisztrálása
     * 
     * @param user
     * @return
     * @throws DaoException
     *             adatbázis hiba
     * @throws ServerException
     *             ha már létezik ilyen nevű felhasználó
     */
    public User register(User user) throws DaoException, ServerException;

    /**
     * Epizód mentése
     * 
     * @param episode
     * @return
     * @throws DaoException
     */
    public Episode save(Episode episode) throws DaoException;

    /**
     * Sorozat mentése
     * 
     * @param series
     * @return
     * @throws DaoException
     */
    public Series save(Series series) throws DaoException;

    /**
     * Keresés színészek alapján. Visszaad minden olyan entitást, amiben
     * szerepel bármelyik a megadott színészek közül
     * 
     * @param actors
     *            keresett színészek nevei
     * @return
     * @throws DaoException
     */
    public List<RegisteredEntity> searchByActors(Set<String> actors) throws DaoException;

    /**
     * Keresés leírás alapján
     * 
     * @param description
     * @return
     * @throws DaoException
     */
    public List<RegisteredEntity> searchByDescription(String description) throws DaoException;

    /**
     * Keresés címkék alapján
     * 
     * @param labels
     * @return
     * @throws DaoException
     */
    public List<RegisteredEntity> searchByLabels(Set<String> labels) throws DaoException;

    /**
     * Epizód/Sorozat keresése cím alapján
     * 
     * @param title
     * @return
     * @throws DaoException
     */
    public List<RegisteredEntity> searchByTitle(String title) throws DaoException;

    /**
     * Sorozatok keresése rendező alapján
     * 
     * @param director
     * @return
     * @throws DaoException
     */
    public List<Series> searchSeriesByDirector(String director) throws DaoException;

    /**
     * Felirat feltöltése
     * 
     * @param episode
     * @param subtitle
     * @param subtitleData
     * @return
     * @throws DaoException
     * @throws ServerException
     */
    public Subtitle uploadSubtitle(Subtitle subtitle) throws DaoException, ServerException;

}
