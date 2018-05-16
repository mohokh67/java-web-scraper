import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Wikipedia {

  public static void main(String[] args) throws IOException {

    Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
    Elements newsHeadlines = doc.select("#mp-itn b a");
    for (Element headline : newsHeadlines) {
        System.out.println(headline.text());
    }

    System.out.println("\n\n ---------------- Page title ------------------\n");
    System.out.println(doc.title());

    System.out.println("\n\n ---------------- Page source ------------------\n");
    System.out.println(doc.outerHtml());
  }

}
