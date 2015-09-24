/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.sje.bookapp.model;

import java.util.List;

/**
 *
 * @author Steven
 */
public class AuthorService {
    private AuthorDaoStrategy dao;

    // constructor
    public AuthorService(AuthorDaoStrategy dao) {
        this.dao = dao;
    }
    
    public List<Author> getAllAuthors() throws Exception{
        return dao.getAllAuthors();
       
    }
    
    public static void main(String[] args){
        
    }
}
