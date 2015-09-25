/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.sje.bookapp.model;

import java.util.Objects;

/**
 *
 * @author Steven
 */
public class Author {
    private Integer AuthorId;
    private String authorName;
    private String dateAdded;

    //constructor
    
    public Author(Integer authorID, String authorName, String dateAdded) {
        this.AuthorId = authorID;
        this.authorName = authorName;
        this.dateAdded = dateAdded;
    }

    public Author() {
    }

    public Author(Integer authorID) {
        this.AuthorId = authorID;
    }
    
    

    //setters

    public void setAuthorId(Integer AuthorId) {
        this.AuthorId = AuthorId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
    
    
    // getters
    public Integer getAuthorId() {
        return AuthorId;
    }

    public String getAuthorName() {
        return authorName;
    }
    
    public String getDateAdded() {
        return dateAdded;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.AuthorId);
        return hash;
    }

  

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (!Objects.equals(this.AuthorId, other.AuthorId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Author{" + "authorID=" + AuthorId + ", authorName=" + authorName + ", dateAdded=" + dateAdded + '}';
    }
    
    
   
    
}
