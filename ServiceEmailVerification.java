package com.example.verification.emailverificationapi;

import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import javax.naming.directory.*;
import  javax.naming.*;


import org.springframework.stereotype.Service;

@Service
public class ServiceEmailVerification {

	public boolean verifyEmailFormat(List<String> emailAddresses) {
		
		if(emailAddresses == null) {
			return false;
		}
		
	    for (String emailAddress : emailAddresses) {
	    	
	        //String regularExp = "^[A-Za-z0-9+_-]{2,12}+@([A-Za-z]+)\\.[a-z]{2,3}$";
	    	String regularExp = "^[A-Za-z0-9+_.-]+@(.+)$";
	        
	        if (!emailAddress.matches(regularExp)) {
	        	
	            return false; 
	        }
	    }
	    return true; 
	}

	public boolean verifyMXRecords(List<String> emailAddresses) throws NamingException {
	    
	    if(emailAddresses == null) {
	        return false;
	    }
	    
	    String str = "";
	    
	    for(String emailAddress : emailAddresses) {
	        
	    	// Extracting domain from email address
	        str = emailAddress.substring(emailAddress.indexOf("@") + 1);
	        
	        // Setting up JNDI environment properties for DNS lookup
	        Hashtable<String, String> env = new Hashtable<String, String>();
	        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
	        
	        // Performing DNS lookup to get MX records for the domain
	        DirContext ictx = new InitialDirContext( env );
	        Attributes attrs = ictx.getAttributes("dns:/" + str, new String[] { "MX" }); 
	        Attribute attr = attrs.get( "MX" );
	        
	        // If no MX record found, return false
	        if(attr == null) {
	            
	            return false;
	        }
	    }
	    
	    return true;
	}

	public boolean verifySMTPConnection(List<String> emailAddresses) {
	    
	    if(emailAddresses == null) {
	        return false;
	    }
	    
	    String str = "";
	    
	    for(String emailAddress : emailAddresses) {
	        
	        str = emailAddress.substring(emailAddress.indexOf("@") + 1);
	        
	        if(str == null) {
	            
	            return false;
	        }
	        
	        // Setting up properties for SMTP connection
	        Properties properties = new Properties();
	        properties.setProperty("mail.smtp.host", "smtp." + str); 
	        
	        // Creating a mail session
	        Session session = Session.getDefaultInstance(properties);
	        
	        try {
	        	// Creating a transport and connecting to the SMTP server
	            Transport transport = session.getTransport("smtp");
	            transport.connect("smtp." + str, 587, null, null);
	            transport.close();
	            
	            return true;
	            
	        } catch (MessagingException e) {
	        	// If an exception occurs (e.g., connection failure), return false
	            return false;
	        }
	    }
	    return true;
	}


}
