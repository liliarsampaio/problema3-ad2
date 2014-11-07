package dao;

import models.Artist;

import java.util.List;

public class ArtistDao extends GenericDao<Long, Artist> {

    private final String HQL_MINIFIED_ARTIST = "Select new Artist(a.id, a.name, a.msdId) from Artist a";

    public ArtistDao() {
        super(Artist.class);
    }

    public List<Artist> artists() {
        return GenericDao.createQuery(HQL_MINIFIED_ARTIST).getResultList();
    }
}
