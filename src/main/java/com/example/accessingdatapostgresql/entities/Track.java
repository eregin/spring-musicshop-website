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

    @Column(name = "composer")
    private String composer;

    @Column(name = "milliseconds")
    private Integer duration;

    @Column(name = "bytes")
    private Integer sizeByte;

    @Column(name = "unitprice")
    private Double unitPrice;

    @ManyToMany
    @JoinTable(name = "sales_items",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "sale_id"))
    private List<Sale> sales;

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

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

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
