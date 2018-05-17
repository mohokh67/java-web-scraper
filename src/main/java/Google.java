import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Google {

  //private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36";
  private static final String ENDPOINT = "https://www.google.com/search?q=";
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static void main(String[] args) throws IOException {

    toText("jsoup");
    toCSV("jsoup");
    toConsole("jsoup");
    toJSON("jsoup");

  }

  private static void toText(String query) throws IOException{
    Document doc = Jsoup.connect(ENDPOINT + URLEncoder.encode(query, "UTF-8")).get();

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
    Document doc = Jsoup.connect(ENDPOINT + URLEncoder.encode(query, "UTF-8")).get();
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
    Document doc = Jsoup.connect(ENDPOINT + URLEncoder.encode(query, "UTF-8")).get();

    // In case of Google block this code, use the real USER_AGENT to get oer this problem
    //Document doc = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(query, "UTF-8")).userAgent(USER_AGENT).get();

    for(Element result : doc.select("h3.r a")) {
      String title = result.text();
      String url = result.attr("href");

      System.out.println(title + " ==> " + url);
    }
  }

  private static void toJSON(String query) throws IOException{
    Document doc = Jsoup.connect(ENDPOINT + URLEncoder.encode(query, "UTF-8")).get();

    // In case of Google block this code, use the real USER_AGENT to get oer this problem
    //Document doc = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(query, "UTF-8")).userAgent(USER_AGENT).get();

    final List<SearchResult> resultList = new ArrayList<SearchResult>();

    for(Element result : doc.select("h3.r a")) {
      String title = result.text();
      String url = result.attr("href");

      resultList.add(new SearchResult(title, url));
    }

    OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(new File("search.result.json"), resultList);
  }
}
