/*
 *************************************
 * Created by IntelliJ IDEA          *
 * User: Nhat                     *
 * Email: nhatnpa2508@gmail.com      *
 * Date: 7/29/2019                     *
 * Time: 3:17 PM                     *
 *************************************
 */

package com.codegym.flicker.service.impl;

import com.codegym.flicker.model.Album;
import com.codegym.flicker.model.Image;
import com.codegym.flicker.repository.IImageRepository;
import com.codegym.flicker.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class ImageServiceImpl implements IImageService {

    @Autowired
    private IImageRepository imageRepository;

    @Override
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Page<Image> findAll(Pageable pageable) {
        return imageRepository.findAll(pageable);
    }

    @Override
    public Image findById(Long id) {
        return imageRepository.findOne(id);
    }

    @Override
    public void save(Image image) {
        imageRepository.save(image);
    }

    @Override
    public void remove(Long id) {
        imageRepository.delete(id);
    }

    @Override
    public Iterable<Image> findAllByAlbum(Album album) {
        return imageRepository.findAllByAlbum(album);
    }

    @Override
    public Iterable<Image> findAllByAlbumAndDelete(Long idAlbum) {
        return imageRepository.findAllByAlbumAndDelete(idAlbum);
    }

    @Override
    public Iterable<Image> findAllByDeleteAndAlbumDelete() {
        return imageRepository.findAllByDeleteAndAlbumDelete();
    }

    @Override
    public Page<Image> findAllByDeleteAndAlbumDelete(Pageable pageable){
        return imageRepository.findAllByDeleteAndAlbumDelete(pageable);
    }
}
