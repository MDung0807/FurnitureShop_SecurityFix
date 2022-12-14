package models.services.google;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class GoogleService implements IGoogleService{
    public static String GOOGLE_CLIENT_ID = "903062961446-ri29gdpngnr71lil7fgkpnckc296eeoa.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = "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";
    public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";

    private static GoogleService instance;

    public static GoogleService getInstance(){
        if(instance == null)
            instance = new GoogleService();
        return instance;
    }


    @Override
    public String getToken(String code, String context) throws IOException {
        String response = Request.Post(GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", GOOGLE_CLIENT_ID)
                        .add("client_secret", GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri",context + "/login-google").add("code", code)
                        .add("grant_type", GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        return jobj.get("access_token").toString().replaceAll("\"", "");
    }

    @Override
    public GoogleModel getGoogleAccountInfo(String accessToken) throws IOException {
        String link = GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        return new Gson().fromJson(response, GoogleModel.class);
    }
}
