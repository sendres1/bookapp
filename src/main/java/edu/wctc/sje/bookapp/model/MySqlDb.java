/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.sje.bookapp.model;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Steven
 */
public class MySqlDb {

    private Connection conn;

    public void openConnection(String driverClass, String url,
            String userName, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);

    }

    public void closeConnection() throws SQLException {
        conn.close();
    }

    public List<Map<String, Object>> findAllRecords(String tableName) throws SQLException {
        List<Map<String, Object>> records = new ArrayList<>();

        String sql = "SELECT * FROM " + tableName;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                record.put(metaData.getColumnName(i), rs.getObject(i));
            }
            records.add(record);
        }
        return records;
    }

    public int deleteByPrimaryKey(String tableName, String primaryKey, Object primaryKeyValue)
            throws SQLException {
        int rowCount = 0;
        String sql;
        if (primaryKeyValue instanceof String) {
            sql = "DELETE FROM " + tableName + " WHERE " + primaryKey + " = " + 
                    "'" + primaryKeyValue.toString() + "'";
        } else {
            sql = "DELETE FROM " + tableName + " WHERE " + primaryKey + " = " + primaryKeyValue.toString();
        }
        System.out.println("sql in deleteByPrimaryKey before call is " + sql);
        Statement stmt = conn.createStatement();
        rowCount = stmt.executeUpdate(sql);

        return rowCount;
    }

    public int deleteByPrimaryKeyPS(String tableName, String primaryKey, Object primaryKeyValue)
            throws SQLException {
        int rowCount = 0;
        String sql;
        sql = "DELETE FROM " + tableName + " WHERE " + primaryKey + " =?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        
      System.out.println("sql in deleteByPrimaryKeyPS before call is " + sql);
      System.out.println("for value of " + primaryKeyValue);
      
        pstmt.setObject(1, primaryKeyValue);
        rowCount = pstmt.executeUpdate();

        return rowCount;
    }

     public int insertPS(String tableName, List columns, List values)
            throws SQLException {
         int insertedRecords = 0;
         PreparedStatement pstmt = null;
         
//  INSERT into tablename    columns      values
        // INSERT INTO `book`.`author` (`author_name`, `author_id`, `date_created`) 
        //  VALUES ('jon three', '3', '2015-09-21');
         
        try {
			pstmt = buildInsertStatementPS(tableName,columns);

			final Iterator i=values.iterator();
			int index = 1;
			while( i.hasNext() ) {
				final Object obj=i.next();
				
					if(obj != null) pstmt.setObject(index++, obj);
							}
			insertedRecords = pstmt.executeUpdate();

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				pstmt.close();
				
			} catch(SQLException e) {
				throw e;
			} // end try
		} // end finally 
         
         
         
         
         // call to build the list of columns and list of values into a prepared stmt
         // buildinsertstateemtn(    
       return insertedRecords;
     }
     
     public PreparedStatement buildInsertStatementPS(String tableName, List columns) throws SQLException{
       StringBuffer  sql = new StringBuffer();
           sql.append("INSERT INTO ");
           sql.append(tableName);
           sql.append(" (");
           final Iterator i = columns.iterator();
           while (i.hasNext()){
               (sql.append( (String)i.next() )).append(", ");
           }
           sql = new StringBuffer( (sql.toString()).substring( 0,(sql.toString()).lastIndexOf(", ") ) + ") VALUES (" );
	   
           for( int numColumns = 0; numColumns < columns.size(); numColumns++ ) {
			sql.append("?, ");
	       }
		final String finalSQL=(sql.toString()).substring(0,(sql.toString()).lastIndexOf(", ")) + ")";
		//System.out.println(finalSQL);
		return conn.prepareStatement(finalSQL);
     }
    
      public int updatePS(String tableName, List colNames, List colValues, String whereField, 
              Object whereValue)
            throws SQLException {
          PreparedStatement pstmt = null;
          int updateCount = 0;
          //now build update.  need a method to fill the sql with the values
          //UPDATE `book`.`author` SET `author_name`='jon threeb' WHERE `author_id`='3';
        // do this in an excpetion handler so that we can depend on the
		// finally clause to close the connection
		try {
			pstmt = buildUpdateStatement(tableName,colNames,whereField);
System.out.println("here is the prepared statement before values:" + pstmt);
			final Iterator i=colValues.iterator();
			int index = 1;
			boolean doWhereValueFlag = false;
			Object obj = null;

			while( i.hasNext() || doWhereValueFlag) {
				if(!doWhereValueFlag){ obj = i.next();}
				// will set any type of object, including number and dates
					if(obj != null) pstmt.setObject(index++, obj);
				
				if(doWhereValueFlag){ break;} // only allow loop to continue one time
				if(!i.hasNext() ) {          // continue loop for whereValue
					doWhereValueFlag = true;
					obj = whereValue;
				}
			}
System.out.println("here is the prepared statement after values:" + pstmt);
			updateCount = pstmt.executeUpdate();

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				pstmt.close();
			} catch(SQLException e) {
				throw e;
			} // end try
		} // end finally


         
         // call to build the list of columns and list of values into a prepared stmt
         // buildinsertstateemtn(    
             return updateCount;
     }
     
      private PreparedStatement buildUpdateStatement(String tableName,List colNames, String whereField)
               throws SQLException{
          //UPDATE `book`.`author` SET `author_name`='jon threeb' WHERE `author_id`='3';
          StringBuffer sb = new StringBuffer();
          sb.append("UPDATE ");
          sb.append(tableName);
          sb.append(" SET ");
          final Iterator i = colNames.iterator();
          while (i.hasNext()){
              sb.append((String)i.next());
              sb.append(" = ?, ");	
          }
          //substing off the last comma
          sb = new StringBuffer( (sb.toString()).substring( 0,(sb.toString()).lastIndexOf(", ") ) );
          sb.append(" WHERE ");
          sb.append(whereField);
          sb.append(" = ?");
          final String finalSQL = sb.toString();
          
          return conn.prepareStatement(finalSQL);
         
      }
     
    public static void main(String[] args)
            throws Exception {
        MySqlDb db = new MySqlDb();
        
        String driverClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/book";
        String username = "root";
        String password = "admin";
       
        String tableName = "author";
        String primaryKey = "author_id";
        String whereKeyValue = "3";
        String primaryKeyValue = "3";
        
        db.openConnection(driverClassName, url, username, password);
     
        int deleteCounter = db.deleteByPrimaryKeyPS(tableName, primaryKey, primaryKeyValue);
        System.out.println("deleteCounter is " + deleteCounter);
        
        List<String> colNames = new ArrayList<>();
        colNames.add("author_name");
        colNames.add(primaryKey);
        colNames.add("date_created");
        
         List<String> colValues = new ArrayList<>();
        colValues.add("jon three");
        colValues.add(primaryKeyValue.toString());
        colValues.add("2015-09-22"); 
        
        int insertCounter = db.insertPS(tableName, colNames, colValues);
              System.out.println("Records inserted = " + insertCounter); 
       
              
           List<Map<String, Object>> records = db.findAllRecords("author");
        for (Map record : records) {
            System.out.println(record);
        }
            
        int updateCounter = db.updatePS(tableName, colNames, colValues, primaryKey, whereKeyValue);
              System.out.println("Records updated = " + updateCounter); 
       
        // lets print the table out      
           List<Map<String, Object>> updatedRecords = db.findAllRecords("author");
        for (Map recordu : updatedRecords) {
            System.out.println(recordu);
        }
        
        db.closeConnection();

    }
}
