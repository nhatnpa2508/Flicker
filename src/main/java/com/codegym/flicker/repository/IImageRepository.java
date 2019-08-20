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
import com.codegym.flicker.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface IImageRepository extends PagingAndSortingRepository<Image, Long> {
    Iterable<Image> findAllByAlbum(Album album);

/*    @Query("select new Post(p.id, p.title, p.teaser, p.feature, p.created, p.likes, p.category) from Post p where p.category = :category")*/

    @Query(value="SELECT * FROM images WHERE images.album_id = :id AND images.isDelete = 0", nativeQuery = true)
    Iterable<Image> findAllByAlbumAndDelete(@Param("id")Long id);

    @Query(value="SELECT * FROM images inner join albums on images.album_id = albums.id WHERE images.isDelete = 0 and albums.isDelete = 0;", nativeQuery = true)
    Iterable<Image> findAllByDeleteAndAlbumDelete();

    @Query(value="SELECT i FROM Image i INNER JOIN Album a ON i.album = a WHERE i.isDelete = 0 AND a.isDelete = 0")
    Page<Image> findAllByDeleteAndAlbumDelete(Pageable pageable);

}
