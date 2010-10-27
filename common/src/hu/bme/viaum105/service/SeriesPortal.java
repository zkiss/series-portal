package hu.bme.viaum105.service;

import hu.bme.viaum105.data.persistent.Episode;
import hu.bme.viaum105.data.persistent.Subtitle;
import hu.bme.viaum105.data.persistent.User;
import hu.bme.viaum105.subtitle.SubtitleData;

public interface SeriesPortal {

    public final static String JNDI_LOCATION = "ejb/SeriesPortalService";

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
