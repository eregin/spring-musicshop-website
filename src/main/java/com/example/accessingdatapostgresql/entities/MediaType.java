package com.example.accessingdatapostgresql.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "media_types")
public class MediaType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mediatypeid")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "mediaType", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Track> tracks;

    /////////////////////////////////////////////
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "TableModels.MediaType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
