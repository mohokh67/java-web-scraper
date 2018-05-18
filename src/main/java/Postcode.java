import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class Postcode {

  final static String ENDPOINT_URL = "https://services.postcodeanywhere.co.uk/Capture/Interactive/Find/v1.00/json3ex.ws?Key=HJ76-UA59-PA53-ZW95&Origin=GBR&Language=en&Container=&Filter=undefined&Instance=null&Test=false&$block=true&$cache=true&Text=";
  final static String ENDPOINT_DETAIL_POSTCODE_URL_ = "https://services.postcodeanywhere.co.uk/Capture/Interactive/Find/v1.00/json3ex.ws?Key=HJ76-UA59-PA53-ZW95&Origin=GBR&Language=en&Container=GB%7CRM%7CWL%7C2BS-SA2&Filter=undefined&Instance=null&Test=false&$block=true&$cache=true&Text=";
  final static String ENDPOINT_DETAIL_ADDRESS_URL = "https://services.postcodeanywhere.co.uk/Capture/Interactive/Find/v1.00/json3ex.ws?Key=HJ76-UA59-PA53-ZW95&Origin=GBR&Language=en&Container=&Filter=undefined&Instance=null&Test=false&$block=true&$cache=true&Text=";

  public static void main(String[] args) throws Exception {

    //findResultsCount("Epsom Road");
    //findResultsCount("SW16 4DE");

    findAddressWithDetails("London road");

    findPostcodeWithDetails("NG24 4TS");

    System.out.println(URLDecoder.decode("GB%7CRM%7CENG%7C0AU-NG6"));
  }

  private static void findResultsCount(String address) throws Exception {
    HttpResponse<JsonNode> response = Unirest.get(ENDPOINT_URL + URLEncoder.encode(address, "UTF-8")).asJson();
    //System.out.println(response.getBody());
    JSONObject result = (JSONObject)response.getBody().getObject().getJSONArray("Items").get(0);
    System.out.println(result.getString("Description"));
  }

  private static void findAddressWithDetails(String address) throws Exception {
    HttpResponse<JsonNode> response = Unirest.get(ENDPOINT_DETAIL_ADDRESS_URL + URLEncoder.encode(address, "UTF-8")).asJson();
    JSONArray elements = response.getBody().getObject().getJSONArray("Items");
    for(Object element: elements) {
      JSONObject result = (JSONObject) element;
      System.out.println(result.getString("Description"));
    }
  }

  private static void findPostcodeWithDetails(String address) throws Exception {
    HttpResponse<JsonNode> response = Unirest.get(ENDPOINT_DETAIL_POSTCODE_URL_ + URLEncoder.encode(address, "UTF-8")).asJson();
    JSONArray elements = response.getBody().getObject().getJSONArray("Items");
    for(Object element: elements) {
      JSONObject result = (JSONObject) element;
      System.out.println(result.getString("Text"));
    }
  }
}
