package com.example.accessingdatapostgresql.controllers;

import com.example.accessingdatapostgresql.entities.*;
import com.example.accessingdatapostgresql.repositories.ShopRepository;
import com.example.accessingdatapostgresql.services.UserService;
import com.example.accessingdatapostgresql.stuff.UserStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class MainController {
    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
////////////////////////
//    @GetMapping("/registration")
//    public String registration(Model model) {
//        User user = new User();
//        model.addAttribute("user", user);
//        return "registration";
//    }
//    @PostMapping("/register")
//    public String createUser(@ModelAttribute(value = "user")User user) {
//        userService.createUser(user);
//        return "redirect:/login";
//    }

    @GetMapping
    public String mainPage() {
        return "menu";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        UserStruct userStruct = new UserStruct();
        model.addAttribute("userStruct", userStruct);
        return "registration";
    }
    @PostMapping("/register")
    public String createUser(@ModelAttribute(value = "userStruct")UserStruct userStruct) {
        User user = new User();
        user.setUsername(userStruct.getName());
        user.setPassword(userStruct.getPassword());
        userService.createUser(user);
        return "redirect:/login";
    }
////////////////////////
    @GetMapping("/artists")
    public String getAllArtists(Model model) {
        model.addAttribute("artists", shopRepository.getAllArtists());
        return "artists";
    }

    @GetMapping("/artist/{artistId}")
    public String getArtistById(Model model, @PathVariable("artistId") Long id) {
        model.addAttribute("artist", shopRepository.getArtistById(id));
        return "artist";
    }

    @GetMapping("/tracks")
    public String getAllTracks(Model model, @RequestParam(value = "word", required = false) String word) {
        model.addAttribute("tracks", shopRepository.getAllTracksFiltered(word));
        model.addAttribute("genres", shopRepository.getAllGenres());
        model.addAttribute("word", word);
        return "tracks";
    }

//    @GetMapping("/tracks")
//    public String getAllTracks(Model model) {
//        model.addAttribute("tracks", shopRepository.getAllTracks());
//        return "tracks";
//    }

    @GetMapping("/track")
    public String getAllTracks(Model model, @RequestParam(value = "id") Integer id) {
        Track track = shopRepository.getTrackById(id);
        model.addAttribute("track", track);
        return "track";
    }

    @GetMapping("/genre")
    public String getAllTracksOfGenre(Model model, @RequestParam(value = "id") Integer id) {
        if (id.equals(0))
            return "redirect:/tracks";
        Genre genre = shopRepository.getGenreById(id);
        model.addAttribute("genre", genre);
        model.addAttribute("genres", shopRepository.getAllGenres());
        return "allTracksOfGenre";
    }

    @GetMapping("/album")
    public String getAllTracksOfAlbum(Model model, @RequestParam(value = "id") Integer id) {
        Album album = shopRepository.getAlbumById(id);
        model.addAttribute("album", album);
        return "allTracksOfAlbum";
    }

}
