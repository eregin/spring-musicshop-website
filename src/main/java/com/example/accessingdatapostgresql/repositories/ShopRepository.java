package com.example.accessingdatapostgresql.repositories;

import com.example.accessingdatapostgresql.entities.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ShopRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Artist> getAllArtists() {
        return entityManager.createQuery("from Artist", Artist.class).getResultList();
    }

    public Artist getArtistById(Long id) {
        return entityManager.find(Artist.class, id);
    }

    public List<Track> getAllTracks() {
        return entityManager.createQuery("from Track", Track.class).getResultList();
    }

    public List<Track> getAllTracksFiltered(String keyword) {
        List<Track> trackList = entityManager.createQuery("from Track", Track.class).getResultList();
        if (keyword == null)
            return trackList;
        return trackList.stream()
                .filter(p -> p.getName().contains(keyword))
                .collect(Collectors.toList());
    }

    public Track getTrackById(Integer id) {
        return entityManager.find(Track.class, id);
    }

    public Genre getGenreById(Integer id) {
        return entityManager.find(Genre.class, id);
    }

    public List<Genre> getAllGenres() {
        return entityManager.createQuery("from Genre", Genre.class).getResultList();
    }

    public Album getAlbumById(Integer id) {
        return entityManager.find(Album.class, id);
    }

    public List<Role> getAllRoles() {return entityManager.createQuery("from Role", Role.class).getResultList();}

}
