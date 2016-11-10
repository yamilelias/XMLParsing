/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlparsing;

import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 *
 * @author A01561056
 */
public class XMLReading {
     public static void readfile() {
        try {

           // Create file to read
           File fXmlFile = new File( "C:\\Users\\A01561056\\Documents\\NetBeansProjects\\XMLParsing\\testing.xml" );
           DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
           Document doc = dBuilder.parse( fXmlFile );
           doc.getDocumentElement().normalize();

           // Print Root element
           System.out.println( "Root element : " + doc.getDocumentElement().getNodeName() );
           NodeList nList = doc.getElementsByTagName( "student" ); // Get every different element by its tag and save it to a NodeList
           System.out.println( "-----------------------" );

           for ( int temp = 0; temp < nList.getLength(); temp++ ) {

              Node nNode = nList.item( temp ); // Get item and save it as a node
              NamedNodeMap atts = nNode.getAttributes();

              // Print student ID (if there is any)
              System.out.println( nNode.getNodeName() );
              Node id = atts.getNamedItem("id");
              if ( id != null )
                  System.out.println("ID: " + id.getNodeValue() );

              if ( nNode.getNodeType() == Node.ELEMENT_NODE ) {

                 Element eElement = (Element) nNode;

                 System.out.println( "First Name : "  + getTagValue("firstname",eElement) );
                 System.out.println( "Last Name : "  + getTagValue("lastname",eElement) );
                 System.out.println( "Nick Name : "  + getTagValue("nickname",eElement) );
                 System.out.println( "Grade : "  + getTagValue("grade",eElement) );

               }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    private static String getTagValue(String sTag, Element eElement){
       NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
       Node nValue = (Node) nlList.item(0);

       return nValue.getNodeValue();
    }
}
