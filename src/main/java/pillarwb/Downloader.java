package pillarwb;

import java.util.Objects;
import java.util.Set;

// import Utils.Utils;
import com.google.gson.JsonObject;

public class Downloader {
    /**
     * The function `downloadFiles` downloads PDF and TXT files from URLs provided in a JSON object and
     * saves them in a specified directory.
     * 
     * @param docs The parameter "docs" is a JsonObject that contains information about the documents
     * to be downloaded. Each document is represented by a key-value pair in the JsonObject, where the
     * key is the document ID and the value is a JsonObject containing the document details.
     * @param mainPath The mainPath parameter is the path where the downloaded files will be saved.
     */
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
