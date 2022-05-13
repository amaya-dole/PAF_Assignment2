package com;

import java.sql.*;

public class Card {
	
	//declare variables
	int cardID;
	String cardno;
	String name;
	String cvv;
	String exDate;
	
	public Card() {
		//constructor   
	   }
	
	public Connection connect() 
	{ 
	 Connection con = null; 
	 
	 try 
	 { 
	 Class.forName("com.mysql.cj.jdbc.Driver"); 
	 con = DriverManager.getConnection("jdbc:mysql://localhost:3307/payment", "root", "root123"); 
	 //For testing
	 System.out.print("Successfully connected-----------------------"); 
	 } 
	 catch(Exception e) 
	 { 
	 e.printStackTrace(); 
	 } 
	 
	 return con; 

	
	}
	
	/////////////////////////////////////////////////////INSERT DATA/////////////////////////////////////////////////////////
	public String insertCard(String cardno, String name, String cvv, String exDate) {
		//check connection to db
		Connection con = connect(); 
		String output;
		if (con == null) 
		{ 
		return "Error while connecting to the database"; 
		}
		
		else {
			//insert data to the database
			String query="insert into card_details(cardID,cardno,name,cvv,exDate) values(?,?,?,?,?)";
		   try {
			PreparedStatement ps=con.prepareStatement(query);
            
			// binding values
			ps.setInt(1, 0);
			ps.setString(2, cardno);
			ps.setString(3, name);
			ps.setString(4, cvv);
			ps.setString(5, exDate);
			
			// execute the statement
			ps.execute();
			output="Card Details Successfully Inserted";
			con.close();
			
		} catch (SQLException e) {
			output="error(check sql statement)";
			
			e.printStackTrace();
		}
		   //display output
		   return output;
		     
		
		}
		
	}
	
	///////////////////////////////////READ DATA////////////////////////////////////////////////////////////
	public String ReadCard() {
		
    	
    	String out="";
    	
    	try
    	{ 
    	//check connection to the database
    	 Connection con = connect(); 
    	if (con == null) 
    	 { 
    	 return "Error while connecting to the database for reading."; 
    	 }
    	
    	out = "<table border=\"1\" class=\"table\"><tr><th>Card Number</th>"
		 		+ "<th>Name</th>"
		 		+ "<th>Update</th>"
		 		+ "<th>Remove</th></tr>"; 
    	try {
    		//execute
    	Statement stmt=con.createStatement();
    	String Query="select * from card_details";
    	ResultSet rs=stmt.executeQuery(Query);
    	
    	while(rs.next()) {
    		//get values
    		String cardID= Integer.toString(rs.getInt("cardID"));
    		String cardno=rs.getString("cardno");
    		String name=rs.getString("name");
    		String cvv=Double.toString(rs.getInt("cvv"));
    	    String exDate=rs.getString("exDate");
    	
    
    	    
    		
    		// Add into the html table
   		 out += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='"+cardID+"'>"+cardno+"</td>"; 
   		 out += "<td>" + name + "</td>";  
   		 // buttons
   		out += "<td><input name='btnUpdate' type='button' value='Update' "
   				 + "class='btnUpdate btn btn-secondary' data-cardid='" + cardID + "'></td>"
   				 + "<td><input name='btnRemove' type='button' value='Remove' "
   				 + "class='btnRemove btn btn-danger' data-cardid='" + cardID + "'></td></tr>"; 
    		
    	}
    	}
    	catch(Exception e) {
    		out = "Error";
    	}
    	
    	out+="</table>";
    	con.close();
    	
    	
    	
    	} 
    	catch (Exception e) 
    	{ 
    	 out = "Error while reading card details."; 
    	 System.err.println(e.getMessage()); 
    	}
    	
    	
    	
    	return out;
    	
    }
	
	
	/////////////////////////////////////////////UPDATE PAYMENT/////////////////////////////////////////
	public String updateCard(String cardID,String cardno, String name, String cvv, String exDate)
    { 
    	
    String output="";
    Connection con = null ;
    try
    { 
    	//check connection
      con = connect(); 
    if (con == null) { 
     return "Error while connecting to the database for update."; 
     } 
     
   
    
    
    String query="Update card_details set cardno=?,name=?,cvv=?,exDate=? Where cardID=?";
    PreparedStatement ps =con.prepareStatement(query);
    ps.setString(1, cardno); 
    ps.setString(2, name); 
    ps.setString(3, cvv); 
    ps.setString(4, exDate); 
    ps.setInt(5,Integer.parseInt(cardID)); 
    
    ps.execute();
    output ="Card details updated successfully!";
    con.close();
    String newCard =ReadCard();
    output = "{\"status\":\"success\",\"data\":\""+newCard+"\"}";
    
    }catch(Exception e){
    	System.out.println("failed id:"+cardID);
    output = "error during update"; 
    System.err.println(e.getMessage());
    }
    
    return output;	
    	
    }
/////////////////////////////////////DELETE DATA//////////////////////////////////

	public String deleteCard(String cardID){ 
		
		String output = ""; 
		
		try{ 
			Connection con = connect(); 
			
			if (con == null){
				return "Error while connecting to the database for deleting."; 
				} 
			// create a prepared statement
			String query = "delete from card_details where cardID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(cardID)); 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			String newItems = ReadCard(); 
			 output = "{\"status\":\"success\",\"data\":\""+newItems+"\"}"; 

		} 
		
		catch (Exception e){ 
			output = "{\"status\":\"error\",\"data\":\"Error while deleting the item.\"}";
			System.err.println(e.getMessage()); 
		} 
		return output; 
}
}
