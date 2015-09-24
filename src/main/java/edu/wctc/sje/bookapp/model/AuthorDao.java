/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.sje.bookapp.model;

import java.util.List;
import java.util.*;



/**
 *
 * @author Steven
 */
public class AuthorDao implements AuthorDaoStrategy {

    private AuthorDaoStrategy db;
    private String driverClass;
    private String driverClassName; // = "com.mysql.jdbc.Driver";
    private String url;  // = "jdbc:mysql://localhost:3306/book";
    private String username;  // = "root";
    private String password; // = "admin";
       
public AuthorDao (AuthorDaoStrategy db, String driverClassName, String url, String username, 
        String password){
    this.db = db;
    this.driverClass = driverClass;
    this.url = url;
    this.username = username;
    this.password = password;
    
}

    @Override
    public List<Author> getAllAuthors() throws Exception {
        db.openConnection(driverClassName, url, username, password);
        List<Author> records = new ArrayList<>();
       
        List<Map<String, Object>> rawData = db.findAllRecords("author");       
         for (Map rawRec : rawData) {
            Author author = new Author();
            Object obj = rawRec.get("author_id");
            author.setAuthorID(Integer.parseInt(obj.toString()));
            
            String name = rawRec.get("author_name") == null ? "" :
                    rawRec.get("author_name").toString();
            author.setAuthorName(name);
            
            String date = rawRec.get("date_added") == null ? "" :
                    rawRec.get("date_added").toString();
            author.setDateAdded(date);
            records.add(author);
                                   
        }
        return records;
     
    }
    
    
}
