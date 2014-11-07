package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import dao.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.TABLE;

@Entity(name = "Artist")
@JsonInclude(NON_DEFAULT)
public class Artist extends Model<Long, Artist> {

    @Id
    @SequenceGenerator(name = "ARTIST_SEQUENCE", sequenceName = "ARTIST_SEQUENCE",
            allocationSize = 1, initialValue = 0)
    @GeneratedValue(strategy = TABLE)
    private long id;

    private String msdId;
    private String name;

    public String getMsdId() {
        return msdId;
    }

    @ManyToMany(cascade = ALL, fetch = LAZY)
    @JoinTable(name = "SIMILARITY", joinColumns = @JoinColumn(name = "TARGET"),
            inverseJoinColumns = @JoinColumn(name = "SIMILAR"))
    @JsonIgnore
    private List<Artist> similars;

    @ManyToMany(cascade = ALL, fetch = LAZY)
    @JoinTable(name = "ARTIST_TAG",
            joinColumns = @JoinColumn(name = "ARTIST"),
            inverseJoinColumns = @JoinColumn(name = "TAG"))
    private List<Tag> tags;

    @OneToMany(cascade = ALL, fetch = LAZY)
    private List<Song> songs;

    public Artist() {
        super.setEntity(this);
        this.tags = new ArrayList<>();
        this.similars = new ArrayList<>();
        this.songs = new ArrayList<>();
    }

    public Artist(Long id, String name, String msdId){
        this();
        this.id = id;
        this.name = name;
        this.msdId = msdId;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMsdId(String msdId) {
        this.msdId = msdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tag> getTags() {
        return Collections.unmodifiableList(tags);
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag t) {
        this.tags.add(t);
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song s) {
        this.songs.add(s);
    }

    public List<Artist> getSimilars() {
        return similars;
    }

    public void setSimilars(List<Artist> similars) {
        this.similars = similars;
    }

    public void addSimilar(Artist artist) {
        this.similars.add(artist);
    }

    public String toString(){
        return name + id;
    }
}