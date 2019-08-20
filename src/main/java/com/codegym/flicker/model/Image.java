/*
 *************************************
 * Created by IntelliJ IDEA          *
 * User: Nhat                     *
 * Email: nhatnpa2508@gmail.com      *
 * Date: 7/29/2019                     *
 * Time: 3:12 PM                     *
 *************************************
 */

package com.codegym.flicker.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String photo;

    @NotEmpty
    private String title = "Image";

    @NotEmpty
    private String author;

    private boolean isDelete = false;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(updatable = false)
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastModifiedEdit;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;


    @Override
    public String toString(){
        return String.format("Image[id=%d,title='%s',content='%s']",id, title,author);
    }

    public Image() {
    }

    public Image(String photo, String title, String author) {
        this.photo = photo;
        this.title = title;
        this.author = author;
    }

    public Image(String photo, String title, String author, boolean isDelete, Date dateCreated, Date lastModifiedEdit) {
        this.photo = photo;
        this.title = title;
        this.author = author;
        this.isDelete = isDelete;
        this.dateCreated = dateCreated;
        this.lastModifiedEdit = lastModifiedEdit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        this.isDelete = delete;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastModifiedEdit() {
        return lastModifiedEdit;
    }

    public void setLastModifiedEdit(Date lastModifiedEdit) {
        this.lastModifiedEdit = lastModifiedEdit;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
