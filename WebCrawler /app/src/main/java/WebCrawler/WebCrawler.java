package WebCrawler;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

//implementing runnable allows us to run each webcrawler on a different thread
public class WebCrawler implements Runnable
{
    //array used to store the data received from the bot
    private ArrayList<String> visitedlink = new ArrayList<String>();
    private String firstUrl;
    private Thread thread;
    private int tracker;
    //limits how deep the bot will go
    private static final int MAX_DEPTH = 3;

    //constructor for WebCrawler
    public WebCrawler(String link, int num)
    {
        firstUrl = link;
        //keeps count of which bot is doing what
        tracker = num;
        thread = new Thread(this);
        //bot starts running
        thread.start();
    }

    @Override
    public void run()
    {

        crawl(1,firstUrl);
    }


    private void crawl(int depth, String url){
        //ensures that it stays within the max depth set
        if (depth <= MAX_DEPTH){
            Document doc = request(url);
            //conditional to exclude null documents
            if (doc!= null){
                //html syntax used to select the link
                for(Element link: doc.select("a[href]")){
                    String next_link = link.absUrl("href");
                    //conditional to increase the depth and proceed to the next link
                    if(visitedlink.contains(next_link)==false){
                        crawl(depth++,next_link);
                    }
                }
            }
        }
    }
    private Document request(String url){
    try{
        org.jsoup.Connection connection = Jsoup.connect(url);
        Document doc = connection.get();
        //conditional to print specific information received from the bot
        if(connection.response().statusCode() == 200) {
            System.out.print("Bot Number: " + tracker + "\t");
            //stores the title
            String title = doc.title();
            System.out.println("Title of Webpage: " + title + " URL: " + url);
            //stores the link in the variable below
            visitedlink.add(url);
            return doc;
        }
        return null;
    }
    catch(IOException e)
    {
        return null;
    }
}

    public Thread getThread()
    {
        //needed to access the thread, called on in the main class
        return thread;
    }
}