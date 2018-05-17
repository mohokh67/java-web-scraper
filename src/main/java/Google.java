import com.sun.xml.internal.ws.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

public class Google {

  //public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36";
  //public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36";

  public static void main(String[] args) throws IOException {

    toText("jsoup");

    toCSV("jsoup");

    toConsole("jsoup");

  }

  private static void toText(String query) throws IOException{
    Document doc = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(query, "UTF-8")).get();

    // In case of Google block this code, use the real USER_AGENT to get oer this problem
    //Document doc = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(query, "UTF-8")).userAgent(USER_AGENT).get();

    final PrintWriter file = new PrintWriter("search.result.txt");

    for(Element result : doc.select("h3.r a")) {
      String title = result.text();
      String url = result.attr("href");

      file.write(title + " ==> " + url + "\n");
      //System.out.println(title + " ==> " + url);
    }

    file.close();
  }

  private static void toCSV(String query) throws IOException{
    Document doc = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(query, "UTF-8")).get();
    // In case of Google block this code, use the real USER_AGENT to get oer this problem
    //Document doc = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(query, "UTF-8")).userAgent(USER_AGENT).get();

    final PrintWriter file = new PrintWriter("search.result.csv");

    file.write("Title; URL\n");

    for(Element result : doc.select("h3.r a")) {
      String title = result.text();
      String url = result.attr("href");

      file.write(title + "; " + url + "\n");
    }

    file.close();
  }

  private static void toConsole(String query) throws IOException{
    Document doc = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(query, "UTF-8")).get();

    // In case of Google block this code, use the real USER_AGENT to get oer this problem
    //Document doc = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(query, "UTF-8")).userAgent(USER_AGENT).get();

    for(Element result : doc.select("h3.r a")) {
      String title = result.text();
      String url = result.attr("href");

      System.out.println(title + " ==> " + url);
    }
  }
}
