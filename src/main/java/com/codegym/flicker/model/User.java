/*
 *************************************
 * Created by IntelliJ IDEA          *
 * User: Nhat                     *
 * Email: nhatnpa2508@gmail.com      *
 * Date: 7/29/2019                     *
 * Time: 3:11 PM                     *
 *************************************
 */

package com.codegym.flicker.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Email
    @Column(updatable = false)
    private String email;

    private String password;

    private String salt;
    @Size(min =2, max = 20)
    private String nickname;

    private String role = "User";

    private String image;

    private String information = "No information";

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(updatable = false)
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date lastModifiedEdit;

    @OneToMany(mappedBy = "user")
    private Set<Album> albums;

    @Override
    public String toString(){
        return String.format("User[id=%d,email='%s',password='%s']",id, email,password);
    }

    public User() {
    }

    public User(@Email String email, String password, String salt) {
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

    public User(@Email String email,String nickname, String password, String salt, String role, String image, String information, Date dateCreated, Date lastModifiedEdit) {
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.role = role;
        this.image = image;
        this.information = information;
        this.dateCreated = dateCreated;
        this.lastModifiedEdit = lastModifiedEdit;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
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

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
