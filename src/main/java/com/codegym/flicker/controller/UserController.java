/*
 *************************************
 * Created by IntelliJ IDEA          *
 * User: Nhat                     *
 * Email: nhatnpa2508@gmail.com      *
 * Date: 7/29/2019                     *
 * Time: 3:20 PM                     *
 *************************************
 */

package com.codegym.flicker.controller;

import com.codegym.flicker.model.User;
import com.codegym.flicker.model.hash.Hashing;
import com.codegym.flicker.model.upload.UserImageUpload;
import com.codegym.flicker.service.IImageService;
import com.codegym.flicker.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Controller
@SessionAttributes("user")
public class UserController {

    private static String UPLOAD_LOCATION = "E:\\CodeGym\\Nhat\\NhatModule02\\CaseStudy\\FlickerApp\\src\\main\\webapp\\WEB-INF\\images\\";

    @Autowired
    private IUserService userService;

    @Autowired
    private IImageService imageService;

    private Hashing hash = new Hashing();

    @ModelAttribute("user")
    public User getCart() {
        return new User();
    }

    @GetMapping(value="/users",produces = "application/json;charset=UTF-8")
    public ModelAndView listCategory(){
        Iterable<User> users = userService.findAll();
        ModelAndView modelAndView = new ModelAndView("/user/list");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping(value="/register",produces = "application/json;charset=UTF-8")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/login/register");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping(value="/register",produces = "application/json;charset=UTF-8")
    public ModelAndView register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) throws NoSuchAlgorithmException {
        if(bindingResult.hasFieldErrors()){
            return new ModelAndView("/login/register");
        }
        User userTerm = userService.getUserByEmail(user.getEmail());
        ModelAndView modelAndView = new ModelAndView("/login/register");
        if (userTerm != null){
            modelAndView.addObject("user", user);
            modelAndView.addObject("message", "Email has been initialized");
            return modelAndView;
        } else {
            String passwordTemp = user.getPassword();
            byte[] salted = this.hash.createSalt();
            String salt = this.hash.byteToString(salted);
            String password = this.hash.generateHash(passwordTemp, salted);
            user.setPassword(password);
            user.setSalt(salt);
            String nickname = user.getEmail().substring(0,5);
            user.setNickname(nickname);
            user.setImage("no.jpg");
            userService.save(user);
            modelAndView.addObject("user", new User());
            modelAndView.addObject("message", "New user created successfully");
            return modelAndView;
        }
    }

    @GetMapping(value="/editInfo/{id}",produces = "application/json;charset=UTF-8")
    public String viewEditInfoForm(@PathVariable("id") Long id, @ModelAttribute("user") User user, HttpSession session, ModelMap model) {
        if (user != null) {
            User userDB = userService.findById(id);
            session.setAttribute("user", userDB);
            UserImageUpload userImageUpload = new UserImageUpload();
            userImageUpload.setEmail(userDB.getEmail());
            userImageUpload.setInformation(userDB.getInformation());
            userImageUpload.setNickname(userDB.getNickname());
            userImageUpload.setId(userDB.getId());
            userImageUpload.setImage(userDB.getImage());
            model.addAttribute("userImageUpload", userImageUpload);
            model.addAttribute("user", userDB);
            return "/userManager/editInformation";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/editInfo")
    public ModelAndView updateInfo(@ModelAttribute("userImageUpload") UserImageUpload userImageUpload,
                                   @ModelAttribute("user") User user, HttpSession session, BindingResult bindingResult) {
        if(bindingResult.hasFieldErrors()){
            return new ModelAndView("/userManager/editInformation");
        }
        MultipartFile file = userImageUpload.getMultipartFile();
        String path = UPLOAD_LOCATION + file.getOriginalFilename();

        try {
            FileCopyUtils.copy(file.getBytes(), new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String pathFile = file.getOriginalFilename();
        User userDB = userService.findById(userImageUpload.getId());

        userDB.setNickname(userImageUpload.getNickname());
        userDB.setImage(pathFile);
        userDB.setInformation(userImageUpload.getInformation());
        userService.save(userDB);
        session.setAttribute("user", userDB);
        ModelAndView modelAndView = new ModelAndView("/userManager/editInformation");
        modelAndView.addObject("user", userDB);
        modelAndView.addObject("message", "Information updated successfully");
        return modelAndView;
    }

    @GetMapping("/editPass/{id}")
    public String viewEditPassForm(@PathVariable("id") Long id, @ModelAttribute("user") User user, HttpSession session, ModelMap model) {
        if (user != null) {
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            return "userManager/editPass";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/editPass")
    public String updatePass(@ModelAttribute("user") User user, @RequestParam("newPassword") String newPasswordTemp,
                             @RequestParam("reNewPassword") String reNewPassword, HttpSession session, ModelMap model)
            throws NoSuchAlgorithmException {
        User userDB = userService.findById(user.getId());
        String saltDB = userDB.getSalt();
        byte[] saltedDB = this.hash.stringToByte(saltDB);

        String passInput = user.getPassword();
        String passwordInput = this.hash.generateHash(passInput, saltedDB);

        if (!newPasswordTemp.equals(reNewPassword) || newPasswordTemp.equals(passInput)) {
            model.addAttribute("user", userDB);
            model.addAttribute("message", "Wrong input");
        } else {
            if (!passwordInput.equals(userDB.getPassword())) {
                model.addAttribute("user", userDB);
                model.addAttribute("message", "Wrong input");
            } else {
                String newPasswordDB = this.hash.generateHash(newPasswordTemp, saltedDB);
                userDB.setPassword(newPasswordDB);
                userService.save(userDB);
                session.setAttribute("user", userDB);
                model.addAttribute("user", userDB);
                model.addAttribute("message", "Password updated successfully");
            }
        }
        return "userManager/editPass";
    }
}
