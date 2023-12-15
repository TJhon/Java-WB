package pillarwb;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// import Cod;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Utils {
    /**
     * The function `responseAPI` sends a GET request to a specified URL, retrieves the response body,
     * parses it as JSON, and returns the "documents" object from the JSON response.
     * 
     * @param url The `url` parameter is a string that represents the URL of the API endpoint that you
     * want to make a request to.
     * @return The method is returning a JsonObject.
     */
    public static  JsonObject responseAPI(String url) throws Exception{
        Gson gson = new Gson();
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(url))
                .build();
        HttpResponse<String> getResponse = HttpClient
                .newHttpClient()
                .send(getRequest, HttpResponse.BodyHandlers.ofString());
        String responseBody = getResponse.body();
        JsonElement json = gson.fromJson(responseBody, JsonElement.class);
        JsonObject jsonObject = json.getAsJsonObject();
        int total = jsonObject.get("total").getAsInt();
        System.out.println(total);
        JsonObject docs = jsonObject.getAsJsonObject("documents");
        return docs;
    }

    /**
     * The function `downloadFileFromURL` downloads a file from a given URL and saves it to a specified
     * destination path.
     * 
     * @param urlString The URL of the file you want to download.
     * @param destination The "destination" parameter is the name of the file that you want to save the
     * downloaded content as. It should include the file extension as well. For example, if you want to
     * save the downloaded content as a PDF file, you can pass "file.pdf" as the destination parameter.
     * @param wherePath The `wherePath` parameter is the directory path where you want to save the
     * downloaded file.
     */
    public static void downloadFileFromURL(String urlString, String destination, String wherePath) {
        try {
            URL url = new URL(urlString);
            InputStream inputStream = url.openStream();
//            TimeUnit.SECONDS.sleep(2);
            String to_file = wherePath + destination;
            FileOutputStream fileOutputStream = new FileOutputStream(to_file);
//            FileOutputStream fileOutputStream = new FileOutputStream("PDF/file.pdf");
            byte[] bytes = new byte[1024];
            int lena;
            while ((lena = inputStream.read(bytes)) != -1){
                fileOutputStream.write(bytes, 0, lena);
            }
            fileOutputStream.close();
            inputStream.close();
            System.out.println("complete");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * The function takes a target string and a JsonObject, and returns a modified URL string by
     * replacing "http:" with "https:" and "//documents." with "//documents1.".
     * 
     * @param target The target parameter is a String that represents the key of the value we want to
     * retrieve from the JsonObject doc.
     * @param doc The "doc" parameter is a JsonObject that contains some data. It is used to retrieve a
     * value associated with the "target" key.
     * @return The method is returning a modified URL string.
     */
    public static String validURL(String target, JsonObject doc){
        String url = doc.get(target)
                .getAsString().
                replace("http:", "https:")
                .replace("//documents.", "//documents1.");
        return url;
    }
    /**
     * The function adds a double forward slash to the end of a URL if it doesn't already have one.
     * 
     * @param path The parameter "url" is a string representing a URL.
     * @return The method is returning the modified URL string.
     */
    public static String addValid(String path) {
        if (!path.endsWith("//")) {
            path += "//";
        }
        return path;
    }
    /**
     * The function "validString" checks if a given string is empty and returns null if it is,
     * otherwise it returns the string itself.
     * 
     * @param str The parameter "str" is a string that is being passed into the "validString" method.
     * @return The method is returning the input string if it is not empty. If the input string is
     * empty, it is returning null.
     */
    public static String validString(String str){
        String string;
        if(str.isEmpty()){
            string = null;
            return string;
        }
        return str;
    }
    /**
     * The function `requestAPI` takes in various parameters related to a World Bank API request and
     * constructs the corresponding URL to retrieve the desired data.
     * 
     * @param country1 The country code or name for which you want to request data.
     * @param start_date1 The start date of the API request in the format "yyyy-MM-dd".
     * @param end_date1 The end date for the API request. It is a string representing a date in the
     * format "YYYY-MM-DD".
     * @param lndinstr The "lndinstr" parameter is used to specify the type of lending instrument for
     * the API request. It is a string that represents the lending instrument, such as "Investment
     * Project Financing".
     * @param project_id1 The project ID is a unique identifier for a specific project. It is used to
     * search for a specific project in the API.
     * @param rows The number of rows to be returned in the API response.
     * @return The method returns a string that represents the URL for making an API request to the
     * World Bank database. The URL includes various parameters such as country code, start date, end
     * date, project ID, and number of rows to retrieve.
     */
    public static String requestAPI(
            String country1,
            String start_date1,
            String end_date1,
            String lndinstr,
            String project_id1,
            int rows
    )     {
        // fct=docty_exact,count_exact,lang_exact,disclstat_exact&docty_key=540656&contrycode_exact=PE&lndinstr=Investment+Project+Financing&strdate=2018-01-01&enddate=2020-01-01&rows=1000
        String mainURL = "https://search.worldbank.org/api/v2/wds?format=json&fct=docty_exact,count_exact,lang_exact,disclstat_exact&docty_key=540656";
        String cod_country1 = Cod.getCode(country1);
        String lndinstr1 = validString(lndinstr);
        String start_date = validString(start_date1);
        String end_date = validString(end_date1);
        String project_id = validString(project_id1);
        String cod_country = validString(cod_country1);

        if (project_id != null) {
            System.out.println("Search project id");
            return mainURL + "&projectid=" + project_id + "&rows=1";
        }
        if (cod_country != null) {
            mainURL = mainURL + "&countrycode_exact=" + cod_country;
        }
        if (lndinstr != null) {
            mainURL = mainURL + "&lndinstr=" + lndinstr.replace(" ", "+");
        }
        if (start_date != null) {
            mainURL = mainURL + "&strdate=" + start_date;
        }
        if (end_date != null) {
            mainURL = mainURL + "&enddate=" + end_date;
        }

        return mainURL + "&rows=" + rows;
    }


}
