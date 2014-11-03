package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import dao.GenericDao;
import dao.Model;
import models.Artist;
import models.Song;
import models.Tag;
import play.db.jpa.Transactional;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.NoResultException;

import static play.libs.Json.toJson;
import static play.libs.Json.fromJson;
import static play.mvc.BodyParser.Json;

public class Application extends Controller {

    private static GenericDao<Long, Song> songDao = new GenericDao<>(Song.class);
    private static GenericDao<Long, Artist> artistDao = new GenericDao<>(Artist.class);
    private static GenericDao<Long, Tag> tagDao = new GenericDao<>(Tag.class);

    public static Result index() {
        return ok(views.html.index.render());
    }

    @Transactional
    @BodyParser.Of(Json.class)
    public static Result newSong() {
        return persistFromRequestBody(Song.class, "song");
    }

    @Transactional(readOnly = true)
    public static Result songs() {
        return ok(toJson(songDao.findAll()));
    }

    @Transactional(readOnly = true)
    public static Result song(long id) {
        return findById(songDao, id, "song");
    }

    @Transactional
    @BodyParser.Of(Json.class)
    public static Result setSong(long id) {
        return updateFromRequestBody(id, Song.class, "song");
    }

    public static Result deleteSong(long id) {
        return play.mvc.Results.TODO;
    }

    @Transactional
    public static Result newArtist() {
        return persistFromRequestBody(Artist.class, "artist");
    }

    @Transactional(readOnly = true)
    public static Result artists() {
        return ok(toJson(artistDao.findAll()));
    }

    @Transactional(readOnly = true)
    public static Result artist(long id) {
        return findById(artistDao, id, "artist");
    }

    @Transactional
    @BodyParser.Of(Json.class)
    public static Result setArtist(long id) {
        return updateFromRequestBody(id, Artist.class, "artist");
    }

    @Transactional
    public static Result deleteArtist(long id) {
        try{
            artistDao.removeById(id);
            artistDao.flush();
        }catch(IllegalArgumentException e){
            return notFound("There is artist with the id " + id);
        }
        return noContent();
    }

    @Transactional
    public static Result newTag() {
        return persistFromRequestBody(Tag.class, "tag");
    }

    @Transactional(readOnly = true)
    public static Result tags() {
        return ok(toJson(tagDao.findAll()));
    }

    @Transactional(readOnly = true)
    public static Result tag(long id) {
        return findById(tagDao, id, "tag");
    }

    @Transactional
    @BodyParser.Of(Json.class)
    public static Result setTag(long id) {
        return updateFromRequestBody(id, Tag.class, "tag");
    }

    public static Result deleteTag(long id) {
        return play.mvc.Results.TODO;
    }

    private static <I, T> Result findById(GenericDao<I, T> dao, I id, String entityName) {
        try {
            return ok(toJson(dao.findById(id)));
        } catch (NoResultException e) {
            return notFound("There is no " + entityName + " in data base with id: " + id);
        }
    }

    private static <I, T extends Model<I, T>> Result updateFromRequestBody(I id, Class<T> EntityClass, String entityName) {
        T jsonObject = null;
        try {
            JsonNode json = request().body().asJson();
            jsonObject = fromJson(json, EntityClass);

            if (!jsonObject.getId().equals(id)) {
                return badRequest("You cannot set the id from a " + entityName);
            }
            jsonObject.update();
        } catch (RuntimeException e) {
            if (e.getCause() instanceof UnrecognizedPropertyException) {
                return badRequest("The json is not a valid json from a " + entityName + ".\n" +
                        e.getMessage());
            } else {
                return internalServerError("An error happened when trying to update the " + entityName);
            }
        }
        return ok("/" + entityName + "/" + jsonObject.getId());
    }

    private static <I, T extends Model<I, T>> Result persistFromRequestBody(Class<T> EntityClass, String entityName) {
        T jsonObject = null;
        try {
            JsonNode json = request().body().asJson();
            jsonObject = fromJson(json, EntityClass);

            jsonObject.save();
        } catch (RuntimeException e) {
            if (e.getCause() instanceof UnrecognizedPropertyException) {
                return badRequest("The json is not a valid json from a " + entityName + ".\n" +
                        e.getMessage());
            } else {
                return internalServerError("An error happened when trying to persist the " + entityName);
            }
        }
        return created("/" + entityName + "/" + jsonObject.getId());
    }
}