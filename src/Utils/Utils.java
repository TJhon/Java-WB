package Utils;

import Countries.Cod;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Utils {
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
    public static String validURL(String target, JsonObject doc){
        String url = doc.get(target)
                .getAsString().
                replace("http:", "https:")
                .replace("//documents.", "//documents1.");
        return url;
    }
    public static String addValid(String url) {
        if (!url.endsWith("//")) {
            url += "//";
        }
        return url;
    }
    public static String validString(String str){
        String string;
        if(str.isEmpty()){
            string = null;
            return string;
        }
        return str;
    }
    public static String requestAPI(
            String country1,
            String start_date1,
            String end_date1,
            String lndinstr,
            String project_id1,
            int rows
    )     {
        String mainURL = "https://search.worldbank.org/api/v2/wds?format=json&fct=docty_exact,count_exact,lang_exact,disclstat_exact&apilang=en&docty_key=540656&order=desc&os=0&srt=docdt";
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
