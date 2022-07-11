package com.example.accessingdatapostgresql.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tracks")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trackid")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name="albumid", nullable=false)
    private Album album;

    @ManyToOne
    @JoinColumn(name="mediatypeid", nullable=false)
    private MediaType mediaType;

    @ManyToOne
    @JoinColumn(name="genreid", nullable=false)
    private Genre genre;

//    @OneToMany(mappedBy = "track", fetch = FetchType.LAZY)
//    private List<SalesItems> salesItems;
//
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "playlist_track",
//            joinColumns = @JoinColumn(name = "TrackId"),
//            inverseJoinColumns = @JoinColumn(name = "PlaylistId"))
//    private List<Playlist> playlistsWith;

    @Column(name = "composer")
    private String composer;

    @Column(name = "milliseconds")
    private Integer duration;

    @Column(name = "bytes")
    private Integer sizeByte;

    @Column(name = "unitprice")
    private Double unitPrice;

    ///////////////////////////////////
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public String getName() {
        return name;
    }

//    public List<SalesItems> getSalesItems() {
//        return salesItems;
//    }
//
//    public void setSalesItems(List<SalesItems> salesItems) {
//        this.salesItems = salesItems;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

//    public List<Playlist> getPlaylistsWith() {
//        return playlistsWith;
//    }
//
//    public void setPlaylistsWith(List<Playlist> playlistsWith) {
//        this.playlistsWith = playlistsWith;
//    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSizeByte() {
        return sizeByte;
    }

    public void setSizeByte(int sizeByte) {
        this.sizeByte = sizeByte;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Track{\t" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", album = " + album.getTitle() +
                ", mediaType = " + mediaType.getName() +
                ", genre = " + genre.getName() +
                ", composer = '" + composer + '\'' +
                ", duration = " + duration/1000 + "sec" +
                ", sizeByte = " + sizeByte/1024 + "kb" +
                ", unitPrice = " + unitPrice + "$" +
                "\t}";
    }
}
