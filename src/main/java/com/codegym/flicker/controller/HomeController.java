/*
 *************************************
 * Created by IntelliJ IDEA          *
 * User: Nhat                     *
 * Email: nhatnpa2508@gmail.com      *
 * Date: 7/31/2019                     *
 * Time: 8:42 AM                     *
 *************************************
 */

package com.codegym.flicker.controller;

import com.codegym.flicker.model.Album;
import com.codegym.flicker.model.Category;
import com.codegym.flicker.model.Image;
import com.codegym.flicker.model.User;
import com.codegym.flicker.model.hash.Hashing;
import com.codegym.flicker.service.IAlbumService;
import com.codegym.flicker.service.ICategoryService;
import com.codegym.flicker.service.IImageService;
import com.codegym.flicker.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Controller
@SessionAttributes("user")
public class HomeController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IAlbumService albumService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IImageService imageService;

    private Hashing hash = new Hashing();

    @ModelAttribute("user")
    public User getUser() {
        return new User();
    }

    @GetMapping(value = {"/","/login"})
    public String loginForm() {
        return "/login/loginForm";
    }

    @GetMapping(value = "/home",produces = "application/x-www-form-urlencoded;charset=UTF-8")
    public String home(@ModelAttribute("user") User user, HttpSession session, Pageable pageable, Model model) {
        if (user.getEmail() != null) {
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            Page<Album> albums = albumService.findAllByDelete(pageable);
            Page<Image> imageList = imageService.findAllByDeleteAndAlbumDelete(pageable);
            model.addAttribute("albums", albums);
            model.addAttribute("imageList", imageList);
            return "/home/index";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/home",produces ="application/x-www-form-urlencoded;charset=UTF-8" )
    public String login(@RequestParam("email") String email, @RequestParam("password") String passwordTemp,
                        HttpSession session, Pageable pageable, Model model) throws NoSuchAlgorithmException {

        if (email == null || passwordTemp == null){
            model.addAttribute("message", "Login fail");
            return "/login/loginForm";
        }
        User user = userService.getUserByEmail(email);
        if (user == null){
            model.addAttribute("message", "Login fail");
            return "/login/loginForm";
        }
        String salt = user.getSalt();
        byte[] salted = hash.stringToByte(salt);
        String password = this.hash.generateHash(passwordTemp, salted);
        String passwordDB = user.getPassword();
        if (!password.equals(passwordDB)) {
            model.addAttribute("message", "Login fail");
            return "/login/loginForm";
        } else {
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            Page<Album> albums = albumService.findAllByDelete(pageable);
            Page<Image> imageList = imageService.findAllByDeleteAndAlbumDelete(pageable);
            model.addAttribute("albums", albums);
            model.addAttribute("imageList", imageList);
            return "/home/index";
        }
    }

    @GetMapping(value="/images",produces = "application/json;charset=UTF-8")
    public String imagePage(@ModelAttribute("user") User user, HttpSession session, ModelMap model) {
        if (user.getEmail() != null) {
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            Iterable<Image> imageList = imageService.findAllByDeleteAndAlbumDelete();
            model.addAttribute("imageList", imageList);
            return "/home/imageList";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value="/imageOfAlbum{id}",produces = "application/json;charset=UTF-8")
    public String imagePage(@PathVariable("id")Long id,@ModelAttribute("user") User user, HttpSession session, ModelMap model) {
        if (user.getEmail() != null) {
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            Album album = albumService.findById(id);
            Iterable<Image> imageList = imageService.findAllByAlbum(album);
            model.addAttribute("album",album);
            model.addAttribute("imageList", imageList);
            return "/home/imageOfAlbum";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value="/albums",produces = "application/json;charset=UTF-8")
    public String albumPage(@RequestParam("str") Optional<String> str, @ModelAttribute("user") User user, HttpSession session, Pageable pageable, ModelMap model) {
        if (user.getEmail() != null) {
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            Long id = 0L;
            if (str.isPresent()) {
                switch (str.get()) {
                    case "Human":
                        id = 1L;
                        break;
                    case "City":
                        id = 2L;
                        break;
                    case "Nature":
                        id = 3L;
                        break;
                    case "Animal":
                        id = 4L;
                        break;
                    default:
                        break;
                }
                if (id > 0) {
                    Category category = categoryService.findById(id);
                    Iterable<Album> albums = albumService.findAllByCategory(category);
                    model.addAttribute("albums", albums);
                    return "/home/albumList";
                }
            }
            Iterable<Album> albums = albumService.findAll();
            model.addAttribute("albums", albums);
            return "/home/albumList";
        } else {
            return "redirect:/login";
        }

    }
    @GetMapping(value="/about-us",produces = "application/json;charset=UTF-8")
    public String aboutUs(HttpSession session, @ModelAttribute("user")User user, Model model){
        if (user.getEmail() != null) {
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            return "/home/about-us";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value="/logout",produces = "application/json;charset=UTF-8")
    public String logoutPage(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("user",new User());
        model.addAttribute("message", "You have logged out successfully");
        return "/login/loginForm";
    }
}
