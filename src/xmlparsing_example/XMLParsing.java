/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlparsing_example;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 *
 * @author A01561056
 */
public class XMLParsing {
    private static String realname="";
        
    public static void main(String args[]){
        String name="aaguilar";
        String pass="microsoft";
        
        saxParsing(name, pass);
    }
    
    public static void saxParsing(String name, String password) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            Handler handler = new Handler();
            String s = "http://cml.chi.itesm.mx:8080/LoginSessionXML-war/CheckUser.jsp?username="+ name +"&password=" + password;
            URL url = new URL(s);
            URLConnection urlconn = url.openConnection();
            InputStream is = urlconn.getInputStream();
            
            saxParser.parse( is, handler );
            
            if(handler.valid){
                realname = handler.getRealname();
            }
            
            System.out.println("" + realname);
            
            is.close();
            
        } catch (Exception e) {
            System.out.println( e.getLocalizedMessage() );
        }
        
    }

    /**
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }
}
