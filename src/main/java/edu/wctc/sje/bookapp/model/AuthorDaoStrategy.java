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
public interface AuthorDaoStrategy {

    List<Author> getAllAuthors() throws Exception;
        
}
