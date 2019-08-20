/*
 *************************************
 * Created by IntelliJ IDEA          *
 * User: Nhat                     *
 * Email: nhatnpa2508@gmail.com      *
 * Date: 7/29/2019                     *
 * Time: 3:21 PM                     *
 *************************************
 */

package com.codegym.flicker.formatter;

import com.codegym.flicker.model.Album;
import com.codegym.flicker.service.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class AlbumFormatter implements Formatter<Album> {

    private IAlbumService albumService;

    @Autowired
    public AlbumFormatter(IAlbumService albumService) {
        this.albumService = albumService;
    }

    @Override
    public Album parse(String text, Locale locale) throws ParseException {
        return albumService.findById(Long.parseLong(text));
    }

    @Override
    public String print(Album object, Locale locale) {
        return "[" + object.getId() + ", " +object.getName() + "]";
    }

}
