package dao;

import models.Artist;
import models.Song;
import models.Tag;

import javax.persistence.Query;
import java.util.List;

public class ArtistDao extends GenericDao<Long, Artist> {

    private final String HQL_MINIFIED_ARTIST = "SELECT new Artist(a.id, a.name, a.msdId) from Artist a";

    public ArtistDao() {
        super(Artist.class);
    }

    public List<Artist> artists() {
        return GenericDao.createQuery(HQL_MINIFIED_ARTIST).getResultList();
    }

    public List<Song> songs(long id) {
        return GenericDao.createQuery("SELECT a.songs FROM Artist a " +
                "WHERE a.id = :id").setParameter("id", id).getResultList();
    }

    public List<Tag> tags(long id) {
        return GenericDao.createQuery("SELECT a.tags FROM Artist a " +
                "WHERE a.id = :id").setParameter("id", id).getResultList();
    }

    public List<Artist> similars(long id) {
        return GenericDao.createQuery("SELECT a.similars FROM Artist a " +
                "WHERE a.id = :id").setParameter("id", id).getResultList();
    }
}
