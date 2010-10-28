package hu.bme.viaum105.service;

import java.util.List;

import hu.bme.viaum105.data.persistent.Episode;
import hu.bme.viaum105.data.persistent.RegisteredEntity;
import hu.bme.viaum105.data.persistent.Series;
import hu.bme.viaum105.data.persistent.Subtitle;
import hu.bme.viaum105.data.persistent.User;
import hu.bme.viaum105.subtitle.SubtitleData;

public interface SeriesPortal {

    /**
     * A JNDI neve az EJB-nek
     */
    public final static String JNDI_NAME = "ejb/SeriesPortal";

    /**
     * Visszaadja az összes sorozatot név szerint rendezve. A lekérdezés
     * oldalakra van tördelve, minden oldalt külön kell lekérdezni.
     * 
     * @param pageSize
     *            maximum hány elem legyen a visszaadott listában
     * @param pageNumber
     *            hányadik oldalt kérdezzük le
     * @return
     * @throws DaoException
     * @throws ServerException
     */
    public List<Series> getAllSeriesPaged(int pageSize, int pageNumber) throws DaoException;

    /**
     * Értékelés
     * 
     * @param registeredEntity
     * @param user
     * @param rate
     * @throws DaoException
     */
    public void rate(RegisteredEntity registeredEntity, User user, int rate) throws DaoException;

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
     * Felirat feltöltése
     * 
     * @param episode
     * @param subtitle
     * @param subtitleData
     * @return
     * @throws DaoException
     * @throws ServerException
     */
    public Subtitle uploadSubtitle(Episode episode, Subtitle subtitle, SubtitleData subtitleData) throws DaoException, ServerException;

}
