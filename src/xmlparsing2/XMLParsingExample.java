package xmlparsing2;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import java.sql.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author alberto
 */
public class XMLParsingExample {

    static String filename = "C:\\Users\\A01561056\\Documents\\NetBeansProjects\\XMLParsing\\testing.xml";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            Connection conn;
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            // root element
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("wad_course");
            doc.appendChild( rootElement );

            String s = 
                "jdbc:mysql://cml.chi.itesm.mx/wad?user=wad&password=p5zVDmq4IGto";
            conn = DriverManager.getConnection( s );

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from student");
            while ( rs.next() ) {
                String i = rs.getString("id");
                String first = rs.getString("firstname");
                String last = rs.getString("lastname");
                String nick = rs.getString("nickname");
                String g = rs.getString("grade");
                
                // student element
                Element student = doc.createElement("student");
                student.setAttribute("id", i);

                // add student to root
                rootElement.appendChild( student );

                // first name
                Element firstname = doc.createElement("firstname");
                firstname.appendChild( doc.createTextNode(first) );
                student.appendChild( firstname );

                // last name
                Element lastname = doc.createElement("lastname");
                lastname.appendChild( doc.createTextNode( last) );
                student.appendChild( lastname );

                // nickname
                Element nickname = doc.createElement("nickname");
                nickname.appendChild( doc.createTextNode(nick) );
                student.appendChild( nickname );

                // grade
                Element grade = doc.createElement("grade");
                grade.appendChild( doc.createTextNode(g));
                student.appendChild( grade );
            }
            
            
            // write to XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource( doc );
            StreamResult result = new StreamResult( new File(filename));
            transformer.transform( source, result);
            
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        
        readfile();
        saxParsing();
    }

    public static void readfile() {
        try {

            File fXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("student");
            System.out.println("-----------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                NamedNodeMap atts = nNode.getAttributes();

                System.out.println(nNode.getNodeName());
                Node id = atts.getNamedItem("id");
                if (id != null) {
                    System.out.println("ID: " + id.getNodeValue());
                }
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("First Name : " + getTagValue("firstname", eElement));
                    System.out.println("Last Name : " + getTagValue("lastname", eElement));
                    System.out.println("Nick Name : " + getTagValue("nickname", eElement));
                    System.out.println("Grade : " + getTagValue("grade", eElement));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);

        return nValue.getNodeValue();
    }
    
    public static void saxParsing() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler() {
                
                String temp = "";
                
                public void startDocument() {
                    System.out.println( "Start document" );
                }
                
                public void startElement(String uri, 
                        String localName, String qName, 
                        Attributes attributes) {
                    System.out.println( "Start Element: " + qName );
                    temp = "";
                }
                
                public void characters(char[] ch, int start, int length) {
                    String chars = new String(ch, start, length);
                    temp += chars;
                    System.out.println( "Characters: " + chars );
                }
                
                public void endElement(String uri, 
                    String localName, String qName) {
                    System.out.println( "End Element: " + qName );
                }
            };
            System.out.println("Entro aquí");
            String s = "http://cml.chi.itesm.mx:8080/LoginSessionXML-war/CheckUser.jsp?username=aaguilar&password=microsoft";
            URL url = new URL(s);
            URLConnection urlconn = url.openConnection();
            InputStream is = urlconn.getInputStream();
            
            // File fXmlFile = new File(filename);
            // InputStream is = new FileInputStream( fXmlFile );
            saxParser.parse( is, handler );
            System.out.println("Terminó esto");
            is.close();
            
        } catch (Exception e) {
            System.out.println( e.getLocalizedMessage() );
        }
        
    }

    
}