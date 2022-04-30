package WebCrawler;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.FileWriter;   // Import the FileWriter class
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.*;

public class crawler implements Runnable {
    private static final int MAX_DEEPTH=5000;
    private ArrayList<String> VisitedLinks= new ArrayList<String>();
    private int level;

    public crawler(){

    }

    @Override
    public void run() {
         crawl(1);
    }

    private void WriteToFile(String URL){
        try {
            FileWriter myWriter = new FileWriter("test.txt");
            myWriter.write(URL);
            myWriter.close();
          } catch (IOException e) {
            System.out.println("An error occurred when Write to file.");
          }
    }
    private String ReadFromFile(int n) {
        try (Stream<String> lines = Files.lines(Paths.get("file.txt"))) {
            String link = lines.skip(n).findFirst().get();
             return link;
        } catch (IOException e) {
            System.out.println("An error when Read from file.");
            return "";
        }
    }

    private void crawl(int LineinFile){       //level init =1 LineinFile init=1
        if(level<=MAX_DEEPTH){                //untill we have 5000 link
            String URL=ReadFromFile(LineinFile); 
            Document doc =request(URL);
            if(doc!=null){
                for(Element link: doc.select("a[href]")){
                    String next_link=link.absUrl("href");
                    if(VisitedLinks.contains(next_link)==false){
                     WriteToFile(next_link);
                     level++;
                    }
                }
                crawl(LineinFile++);
            }
        }


    }
    private Document request(String URL){
        try{
            Connection Con= Jsoup.connect(URL);
            Document doc=Con.get();
            if(Con.response().statusCode()==200){      //to check the statuscode of the Wed
               String title=doc.title();
               System.out.println(title);              // For Depug
               VisitedLinks.add(URL);

               return doc;
            }
            return null;
        }catch(IOException e){
            return null;
        }
    }

    

}
