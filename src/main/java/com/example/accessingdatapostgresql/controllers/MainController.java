package com.example.accessingdatapostgresql.controllers;

import com.example.accessingdatapostgresql.entities.*;
import com.example.accessingdatapostgresql.repositories.ShopRepository;
import com.example.accessingdatapostgresql.services.UserService;
import com.example.accessingdatapostgresql.stuff.CustomerStruct;
import com.example.accessingdatapostgresql.stuff.UserStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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


    @GetMapping
    public String mainPage() {
        return "menu";
    }
////////////////////////
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

    @GetMapping("/account")
    public String currentUserAccount(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByName(principal.getName()));
        return "account";
    }

    @GetMapping("/confirmation")
    public String customerForm(Principal principal, Model model) {
        model.addAttribute("customerStruct", new CustomerStruct());
        model.addAttribute("value", userService.getUserByName(principal.getName()).getCustomer());
        return "confirmation";
    }
    @PostMapping("/confirmation")
    public String userBecomeCustomer(Principal principal, @ModelAttribute(value = "customerStruct")CustomerStruct customerStruct) {
        User user = userService.getUserByName(principal.getName());
        Customer customer = user.getCustomer();
        customer.setFirstName(customerStruct.getFirstName());
        customer.setLastName(customerStruct.getLastName());
        customer.setCompany(customerStruct.getCompany());
        customer.setAddress(customerStruct.getAddress());
        customer.setCity(customerStruct.getCity());
        customer.setState(customerStruct.getState());
        customer.setCountry(customerStruct.getCountry());
        customer.setPostalCode(customerStruct.getPostalCode());
        customer.setPhone(customerStruct.getPhone());
        customer.setEmail(customerStruct.getEmail());
        userService.userCustomer(user);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String userCart(Principal principal, Model model) {
        User user = userService.getUserByName(principal.getName());
        Customer customer = user.getCustomer();
        Sale sale = customer.getSales().stream().filter(x -> x.getStatus().equals(SaleStatus.CURRENT)).findAny().orElse(null);
        Double sum = (double)Math.round(sale.getBuyTracks().stream().mapToDouble(Track::getUnitPrice).sum() * 100) / 100;
        model.addAttribute("cart", sale);
        model.addAttribute("sum", sum);
        return "cart";
    }

    @GetMapping("/addtrack")
    public String addTrackToBucket(Principal principal, @RequestParam(value = "id") Integer id) {
        User user = userService.getUserByName(principal.getName());
        Customer customer = user.getCustomer();
        Track track = shopRepository.getTrackById(id);
        Sale sale = customer.getSales().stream().filter(x -> x.getStatus().equals(SaleStatus.CURRENT)).findAny().orElse(null);
        userService.addToCart(sale, track);
        return "redirect:/tracks";
    }

    @GetMapping("/deltrack")
    public String delTrackFromBucket(Principal principal, @RequestParam(value = "id") Integer id) {
        User user = userService.getUserByName(principal.getName());
        Customer customer = user.getCustomer();
        Track track = shopRepository.getTrackById(id);
        Sale sale = customer.getSales().stream().filter(x -> x.getStatus().equals(SaleStatus.CURRENT)).findAny().orElse(null);
        userService.delFromCart(sale, track);
        return "redirect:/cart";
    }

    @GetMapping("/acceptOrder")
    public String acceptOrder(Principal principal) {
        User user = userService.getUserByName(principal.getName());
        Customer customer = user.getCustomer();
        Sale sale = customer.getSales().stream().filter(x -> x.getStatus().equals(SaleStatus.CURRENT)).findAny().orElse(null);
        userService.approveCart(sale);
        return "redirect:/";
    }
}
