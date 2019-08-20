/*
 *************************************
 * Created by IntelliJ IDEA          *
 * User: Nhat                     *
 * Email: nhatnpa2508@gmail.com      *
 * Date: 7/29/2019                     *
 * Time: 3:13 PM                     *
 *************************************
 */

package com.codegym.flicker.repository;

import com.codegym.flicker.model.Album;
import com.codegym.flicker.model.Category;
import com.codegym.flicker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.support.SimpleTriggerContext;

public interface IAlbumRepository extends PagingAndSortingRepository<Album, Long> {

    Page<Album> findAllByUser(User user, Pageable pageable);

    Album findAlbumByName(String name);

/*    @Query(value="SELECT * FROM albums WHERE albums.user_id = :id AND albums.isDelete = 0", nativeQuery = true)*/
    //@Query(value="SELECT a FROM Album a WHERE a.user = :user AND a.delete = 0")
    //Page<Album> findAllByDeleteIsFalseAndUser(@Param("user")User user, Pageable pageable);

    @Query("SELECT e FROM Album e WHERE e.isDelete = false and e.user = :user")
    Page<Album> findAllByUserAndDelete(@Param("user")User user, Pageable pageable);

    @Query("SELECT e FROM Album e WHERE e.isDelete = false")
    Page<Album> findAllByDelete( Pageable pageable);

    Page<Album> findAllByCategory(Category category, Pageable pageable);

    Iterable<Album> findAllByCategory(Category category);

    Iterable<Album> findAllByUser(User user);

    Page<Album> findAllByUserAndNameContaining(User user, String name,Pageable pageable);
}
