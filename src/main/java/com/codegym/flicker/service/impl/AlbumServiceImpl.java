/*
 *************************************
 * Created by IntelliJ IDEA          *
 * User: Nhat                     *
 * Email: nhatnpa2508@gmail.com      *
 * Date: 7/29/2019                     *
 * Time: 3:18 PM                     *
 *************************************
 */

package com.codegym.flicker.service.impl;

import com.codegym.flicker.model.Album;
import com.codegym.flicker.model.Category;
import com.codegym.flicker.model.User;
import com.codegym.flicker.repository.IAlbumRepository;
import com.codegym.flicker.service.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class AlbumServiceImpl implements IAlbumService {

    @Autowired
    private IAlbumRepository albumRepository;

    @Override
    public Iterable<Album> findAll() {
        return albumRepository.findAll();
    }

    @Override
    public Page<Album> findAll(Pageable pageable) {
        pageable =new PageRequest(pageable.getPageNumber(),12);
        return albumRepository.findAll(pageable);
    }

    @Override
    public Album findById(Long id) {
        return albumRepository.findOne(id);
    }

    @Override
    public void save(Album album) {
        albumRepository.save(album);
    }

    @Override
    public void remove(Long id) {
        albumRepository.delete(id);
    }

    @Override
    public Page<Album> findAllByCategory(Category category, Pageable pageable) {
/*        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"createDate"));*/
        pageable =new PageRequest(pageable.getPageNumber(),12);
        return albumRepository.findAllByCategory(category, pageable);
    }
    @Override
    public Page<Album> findAllByUserAndDelete(User user, Pageable pageable){
        pageable =new PageRequest(pageable.getPageNumber(),12);
        return albumRepository.findAllByUserAndDelete(user, pageable);
    }

    @Override
    public Page<Album> findAllByUser(User user, Pageable pageable) {
        pageable =new PageRequest(pageable.getPageNumber(),12);
        return albumRepository.findAllByUser(user, pageable);
    }

    @Override
    public Iterable<Album> findAllByCategory(Category category) {
        return albumRepository.findAllByCategory(category);
    }

    @Override
    public Iterable<Album> findAllByUser(User user) {
        return albumRepository.findAllByUser(user);
    }

    @Override
    public Page<Album> findAllByDelete(Pageable pageable) {
        pageable =new PageRequest(pageable.getPageNumber(),12);
        return  albumRepository.findAllByDelete(pageable);
    }

    @Override
    public Page<Album> findAllByUserAndNameContaining(User user, String name,Pageable pageable) {
        pageable =new PageRequest(pageable.getPageNumber(),12);
        return albumRepository.findAllByUserAndNameContaining(user, name, pageable);
    }

    @Override
    public Album findAlbumByName(String name) {
        return albumRepository.findAlbumByName(name);
    }


}
