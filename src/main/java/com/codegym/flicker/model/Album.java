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
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 1)
    private String name;

    private String content;

    private String avatar;

    private boolean isPublic = true;

    private boolean isDelete = false;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(updatable = false)
    @DateTimeFormat(pattern = "MM/dd/yyyy",iso = DateTimeFormat.ISO.DATE)
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @DateTimeFormat(pattern = "MM/dd/yyyy",iso = DateTimeFormat.ISO.DATE)
    private Date lastModifiedEdit;

    private Long view = 0L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "album")
    private Set<Image> images;

    @Override
    public String toString(){
        return String.format("Album[id=%d,name='%s',content='%s']",id, name,content);
    }

    public Album() {
    }

    public Album(@Size(min = 1) String name, String content, boolean isPublic, boolean isDelete, Date dateCreated, Date lastModifiedEdit, Long view, String avatar) {
        this.name = name;
        this.content = content;
        this.isPublic = isPublic;
        this.isDelete = isDelete;
        this.dateCreated = dateCreated;
        this.lastModifiedEdit = lastModifiedEdit;
        this.view = view;
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        this.isPublic = aPublic;
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

    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
