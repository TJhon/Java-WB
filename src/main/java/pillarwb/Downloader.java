package pillarwb;

import java.util.Objects;
import java.util.Set;

// import Utils.Utils;
import com.google.gson.JsonObject;

public class Downloader {
    public static void downloadFiles(JsonObject docs, String mainPath){
//        JsonObject docs = Utils.responseAPI("");
        Set<String> keys = docs.keySet(); /// id de los documentos
        for (String key: keys){
            if (!Objects.equals(key, "facets")){
                JsonObject doc_i = docs.getAsJsonObject(key);
                String name = doc_i.get("repnb").getAsString();
                String pdfurl = Utils.validURL("pdfurl", doc_i);
                String txturl = Utils.validURL("txturl", doc_i);
                Utils.downloadFileFromURL(pdfurl, name + ".pdf", mainPath);
                Utils.downloadFileFromURL(txturl, name + ".txt", mainPath);
            }
        }
    }
}
