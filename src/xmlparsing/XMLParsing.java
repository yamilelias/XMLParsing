/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlparsing;

import java.io.File;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import java.sql.*;
import com.mysql.jdbc.Driver;
import java.util.ArrayList;

/**
 *
 * @author A01561056
 */
public class XMLParsing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            ArrayList<Student> students = new ArrayList<Student>();
            Element element;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Connection conn;
            String s = "jdbc:mysql://cml.chi.itesm.mx/wad?user=wad&password=p5zVDmq4IGto";
            conn = DriverManager.getConnection( s );
            // Create statement from connection
            Statement stmt = conn.createStatement();

            // Gather rows
            String sql = "select * from student";
            ResultSet rs = stmt.executeQuery( sql );

            // Root Element
            Document doc = docBuilder.newDocument();
            
            Element rootElement = doc.createElement("wad_course");
            doc.appendChild(rootElement);
            
            // Loop thru results
            while ( rs.next() ) {
                // Student Element
                Element student = doc.createElement("student");
                student.setAttribute("id", rs.getString("id"));

                // Add Student to Root
                // Is doesn't happen anything if it is before adding the name
                rootElement.appendChild(student);

                // Add first name
                element = addElements(doc, "firstname", rs.getString("firstname"));
                student.appendChild(element);
                
                // Add last name
                element = addElements(doc, "lastname", rs.getString("lastname"));
                student.appendChild(element);

                // Add nickname
                element = addElements(doc, "nickname", rs.getString("nickname"));
                student.appendChild(element);

                // Add Grade
                element = addElements(doc, "grade", rs.getString("grade"));
                student.appendChild(element);
            }
            
            // Create XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:\\Users\\A01561056\\Documents\\NetBeansProjects\\XMLParsing\\testing.xml"));
            transformer.transform(source, result);
            
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        
        XMLReading.readfile();
    }
    
    /**
     * 
     * @param doc
     * @param name
     * @param description
     * @return 
     */
    public static Element addElements(Document doc, String name, String description){
        Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(description));
        
        return element;
    }
}
