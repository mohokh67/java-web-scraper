import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;

import java.net.URLEncoder;

public class Postcode {

  final static String ENDPOINT_URL = "https://services.postcodeanywhere.co.uk/Capture/Interactive/Find/v1.00/json3ex.ws?Key=HJ76-UA59-PA53-ZW95&Origin=GBR&Language=en&Container=&Filter=undefined&Instance=null&Test=false&$block=true&$cache=true&Text=";

  public static void main(String[] args) throws Exception {

    // Address
    find("Epsom Road");

    // Post code
    find("SW16 4DE");
  }

  private static void find(String address) throws Exception {
    HttpResponse<JsonNode> response = Unirest.get(ENDPOINT_URL + URLEncoder.encode(address, "UTF-8")).asJson();
    //System.out.println(response.getBody());
    JSONObject result = (JSONObject)response.getBody().getObject().getJSONArray("Items").get(0);
    System.out.println(result.getString("Description"));
  }
}
