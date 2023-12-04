package WebCrawler;
import java.util.ArrayList;
public class App{
    public static void main(String[] args){
        //use of array to run the bot simultaneously
        ArrayList<WebCrawler> test = new ArrayList<>();
        test.add(new WebCrawler("https://www.espn.com",1));
        test.add(new WebCrawler("https://www.nba.com/watch",2));
        test.add(new WebCrawler("https://www.amazon.com",3));

        for (WebCrawler w : test){
            try{
                //calls on thread
                w.getThread().join();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}