import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.apache.http.HttpHost;
import org.json.JSONObject;

public class HttpbinApi {

  final static String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36";

  public static void main(String[] args) throws Exception {
    String getEndpoint = "https://httpbin.org/get";
    String postEndpoint = "https://httpbin.org/post";

    getStringResponse(getEndpoint);
    getJsonResponse(getEndpoint);
    getJsonResponseFromProxy(getEndpoint);

    postFormResponse(postEndpoint);
    postJsonResponse(postEndpoint);
  }

  private static void getStringResponse(String endpoint) throws Exception {
    HttpResponse<String> response = Unirest.get(endpoint).queryString("limit", 13).asString();
    System.out.println(response.getBody());
    System.out.println(response.getHeaders());
  }

  private static void getJsonResponse(String endpoint) throws Exception {
    Unirest.setDefaultHeader("User-Agent", USER_AGENT);
    HttpResponse<JsonNode> response = Unirest.get(endpoint).queryString("limit", 13).asJson();

    System.out.println("IP Address: " + response.getBody().getObject().getString("origin"));
    System.out.println(response.getBody().getObject().getJSONObject("headers"));
    System.out.println("User-Agent: " + response.getBody().getObject().getJSONObject("headers").getString("User-Agent"));
  }

  private static void getJsonResponseFromProxy(String endpoint) throws Exception {
    Unirest.setDefaultHeader("User-Agent", USER_AGENT);

    // https://free-proxy-list.net/
    Unirest.setProxy(new HttpHost("77.52.188.230", 3128));

    HttpResponse<JsonNode> response = Unirest.get(endpoint).queryString("limit", 13).asJson();

    System.out.println("IP Address: " + response.getBody().getObject().getString("origin"));
    System.out.println("User-Agent: " + response.getBody().getObject().getJSONObject("headers").getString("User-Agent"));
  }

  private static void postFormResponse(String endpoint) throws Exception {
    HttpResponse<String> response = Unirest.post(endpoint).field("name", "MoHo").asString();
    System.out.println(response.getBody());
  }

  private static void postJsonResponse(String endpoint) throws Exception {
    JSONObject json = new JSONObject().put("name", "moho");

    HttpResponse<String> response = Unirest.post(endpoint).body(json).asString();
    System.out.println(response.getBody());
  }

}
