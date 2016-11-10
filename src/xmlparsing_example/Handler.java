/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlparsing_example;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author A01561056
 */
public class Handler extends DefaultHandler{
    String temp = "";
    private boolean validField = false;
    private String realname = "";
    boolean valid = false;

    public void startDocument() {
        //System.out.println( "Start document" );
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        temp = "";
        if(!qName.equals("realname")&&!validField){
            validField = false;
        }
    }

    public void characters(char[] ch, int start, int length) {
        String chars = new String(ch, start, length);
        temp += chars;
        
        if(temp.equals("true")){
            valid = true;
        }
    }

    public void endElement(String uri, String localName, String qName) {
        if(validField){
            realname = temp;
        }
        if(temp.equals("true")){
            validField = true;
        }
    }

    /**
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }
}
