/*
 *************************************
 * Created by IntelliJ IDEA          *
 * User: Nhat                     *
 * Email: nhatnpa2508@gmail.com      *
 * Date: 7/29/2019                     *
 * Time: 3:14 PM                     *
 *************************************
 */

package com.codegym.flicker.service;

import com.codegym.flicker.model.Album;
import com.codegym.flicker.model.Category;
import com.codegym.flicker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface IAlbumService {

    Iterable<Album> findAll();

    Page<Album> findAll(Pageable pageable);

    Album findById(Long id);

    void save(Album album);

    void remove(Long id);

    Page<Album> findAllByCategory(Category category, Pageable pageable);

    Page<Album> findAllByUserAndDelete(User user, Pageable pageable);

    Page<Album> findAllByUser(User user, Pageable pageable);

    Iterable<Album> findAllByCategory(Category category);

    Iterable<Album> findAllByUser(User user);

    Page<Album> findAllByDelete( Pageable pageable);

    Page<Album> findAllByUserAndNameContaining(User user, String name,Pageable pageable);

    Album findAlbumByName(String name);
}
