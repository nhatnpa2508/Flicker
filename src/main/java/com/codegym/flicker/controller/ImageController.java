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

import com.codegym.flicker.model.Album;
import com.codegym.flicker.model.Image;
import com.codegym.flicker.model.upload.UploadImage;
import com.codegym.flicker.model.User;
import com.codegym.flicker.service.IAlbumService;
import com.codegym.flicker.service.IImageService;
import com.codegym.flicker.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@SessionAttributes("user")
public class ImageController {

    private static String UPLOAD_LOCATION = "E:\\CodeGym\\Nhat\\NhatModule02\\CaseStudy\\FlickerApp\\src\\main\\webapp\\WEB-INF\\images\\";

    @Autowired
    private IImageService imageService;

    @Autowired
    private IAlbumService albumService;

    @Autowired
    private IUserService userService;

    @ModelAttribute("albums")
    public Iterable<Album> getAlbums(User user){
        return albumService.findAllByUser(user);
    }

    @ModelAttribute("user")
    public User getUser() {
        return new User();
    }

    @GetMapping(value="/createImg",produces = "application/json;charset=UTF-8")
    public ModelAndView showCreateForm(@ModelAttribute("user")User user, HttpSession session){
        ModelAndView modelAndView = new ModelAndView("userManager/createImage");
        session.setAttribute("user",user);
        modelAndView.addObject("user",user);
        modelAndView.addObject("uploadImage", new UploadImage());
        return modelAndView;
    }

    @PostMapping(value="/createImg",produces = "application/json;charset=UTF-8")
    public ModelAndView saveImage(@Valid @ModelAttribute("uploadImage") UploadImage uploadImage, @ModelAttribute("user") User user, BindingResult bindingResult
                                 , HttpSession session){
        if(bindingResult.hasFieldErrors()){
            return new ModelAndView("/userManager/createImage");
        }
        MultipartFile file = uploadImage.getMultipartFile();
        String pathFile = file.getOriginalFilename();
        String path = UPLOAD_LOCATION + file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = new Image();
        image.setTitle(uploadImage.getTitle());
        image.setAuthor(uploadImage.getAuthor());
        image.setAlbum(uploadImage.getAlbum());
        image.setPhoto(pathFile);
        imageService.save(image);
        session.setAttribute("user",user);
        ModelAndView modelAndView = new ModelAndView("/userManager/createImage");
        modelAndView.addObject("user",user);
        modelAndView.addObject("uploadImage", new UploadImage());
        modelAndView.addObject("message","New image created successfully");
        return modelAndView;
    }

    @GetMapping(value="/editImg/{id}",produces = "application/json;charset=UTF-8")
    public ModelAndView showEditForm(@PathVariable Long id,@ModelAttribute("user") User user, HttpSession session){
        user = userService.getUserByEmail(user.getEmail());
        session.setAttribute("user",user);
        Image image = imageService.findById(id);
        if(image != null){
            ModelAndView modelAndView = new ModelAndView("/userManager/editImg");
            modelAndView.addObject("user",user);
            modelAndView.addObject("image",image);
            modelAndView.addObject("albums",albumService.findAllByUser(user));
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/login");
        }
    }

    @PostMapping(value="/editImg",produces = "application/json;charset=UTF-8")
    public ModelAndView updatePost(@Valid @ModelAttribute("image")Image image,@ModelAttribute("user") User user,
                                   BindingResult bindingResult,HttpSession session){
        if(bindingResult.hasFieldErrors()){
            return new ModelAndView("/userManager/editImg");
        }
        imageService.save(image);
        session.setAttribute("user",user);
        ModelAndView modelAndView = new ModelAndView("/userManager/editImg");
        modelAndView.addObject("user",user);
        modelAndView.addObject("image",image);
        modelAndView.addObject("message","Image updated successfully");
        return modelAndView;
    }

    @GetMapping(value="/deleteImg/{id}",produces = "application/json;charset=UTF-8")
    public ModelAndView showDeleteForm(@PathVariable Long id,@ModelAttribute("user") User user, HttpSession session){
        Image image = imageService.findById(id);
        user = userService.getUserByEmail(user.getEmail());
        session.setAttribute("user",user);
        if(image != null){
            image.setDelete(true);
            imageService.save(image);
            Album album = albumService.findById(image.getAlbum().getId());
            Iterable<Image> imageList = imageService.findAllByAlbumAndDelete(album.getId());
            ModelAndView modelAndView = new ModelAndView("/userManager/index");
            modelAndView.addObject("user",user);
            modelAndView.addObject("album",album);
            modelAndView.addObject("imageList",imageList);
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/login");
        }
    }

}
