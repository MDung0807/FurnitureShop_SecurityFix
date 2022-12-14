package models.services.google;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

public interface IGoogleService {
    String getToken(String code, String context)  throws ClientProtocolException, IOException;
    GoogleModel getGoogleAccountInfo(String accessToken) throws IOException;
}
