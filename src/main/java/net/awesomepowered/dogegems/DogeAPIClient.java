package net.awesomepowered.dogegems;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;

/**
 * A client for https://www.dogeapi.com/
 * From https://github.com/Steveice10/DogeServices-Java
 */

public class DogeAPIClient {

    private static final String BASE = "https://www.dogeapi.com/wow/v2/";
    private String key = DogeGems.dogeSuperSecretAPIKeyThatMustAlwaysBeKeptSecret;

    private String getNewAddressURL = BASE+"?api_key="+key+"&a=get_new_address&address_label=";
    private String checkDogeURL =  BASE+"?api_key="+key+"&a=get_address_received&payment_address=";

    /**
     * Creates a new DogeAPIClient instance without a key.
     */
    public DogeAPIClient() {
        this("");
    }

    /**
     * Creates a new DogeAPIClient instance with a key.
     *
     * @param key API key to use. A null or empty key can be specified if you only want to use methods that don't require a key.
     */
    public DogeAPIClient(String key) throws DogeException {
        this.key = key != null ? key : "";
    }

    /**
     * Gets the API key used by this client.
     *
     * @return The client's API key.
     */
    public String getAPIKey() {
        return this.key;
    }

    //Multithread this shit
    public String getLabledAddress(String label) throws DogeException {
        URL url;
        try {
            url = new URL(getNewAddressURL+label);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setConnectTimeout(2000);
            return parseInputData(con.getInputStream(), "address");
            //con.disconnect();
            //return address;
        } catch (MalformedURLException e) {
            throw new DogeException("URL is not a URL " + e);
        } catch (IOException e) {
            throw new DogeException("Sum ting wong " + e);
        }
    }

    //Multithread this shit
    public String checkDogeFromAddress(String dAddress) throws DogeException {
        URL url;
        try {
           url = new URL(checkDogeURL+dAddress);
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            con.setConnectTimeout(2000);
            return parseInputData(con.getInputStream(), "received");
            //return amount;
        } catch (MalformedURLException e) {
            throw new DogeException("URL is not a URL " + e);
        } catch (IOException e) {
            throw new DogeException("Sum ting wong " + e);
        }
    }

    public String parseInputData(InputStream in, String dataInfo) {
          try {
              final Reader reader = new InputStreamReader(in);
              final BufferedReader br = new BufferedReader(reader);
              JSONParser parser = new JSONParser();
              JSONObject parsed = (JSONObject) parser.parse(br.readLine());
              JSONObject data = (JSONObject) parsed.get("data");
              String parsedData = data.get(dataInfo).toString();
              br.close();
              in.close();
              return parsedData;
        } catch (ParseException e) {
            throw new DogeException("Cannot into parse " + e);
        } catch (IOException e) {
              throw  new DogeException("Sum Ting Wong " + e);
          }
    }
}