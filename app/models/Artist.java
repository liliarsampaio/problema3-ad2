package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import dao.Model;

import javax.persistence.*;
import java.util.ArrayList;
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
    @JoinTable(name = "similarity", joinColumns = @JoinColumn(name = "target"),
            inverseJoinColumns = @JoinColumn(name = "similar"))
    private List<Artist> similars;

    @ManyToMany(cascade = ALL, fetch = EAGER)
    @JoinTable(name = "ARTIST_TAG",
            joinColumns = @JoinColumn(name = "ARTIST"),
            inverseJoinColumns = @JoinColumn(name = "TAG"))
    private List<Tag> tags;

    public Artist() {
        super.setEntity(this);
        this.similars = new ArrayList<>();
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
}