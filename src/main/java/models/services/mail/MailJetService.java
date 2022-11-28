package models.services.mail;


import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;

public class MailJetService implements IMailJetService {
    private static MailJetService instance = null;
    public static MailJetService getInstance(){
        if(instance == null)
            instance = new MailJetService();
        return instance;
    }
    @Override
    public void sendMail(String name, String email) {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient("5469fcd0f7928e3e7e6a30893195bc43", "fd4a44689e032f7633e7dc6f0f31d059", new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "nguyenminhson102002@gmail.com")
                                        .put("Name", "FurSshop"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", email)
                                                .put("Name", name)))
                                .put(Emailv31.Message.SUBJECT, "Đơn xác nhận đặt hàng")
                                .put(Emailv31.Message.HTMLPART, "<h2>Chào " + name + " </h2>, <h4>FurSshop cảm ơn vì đã tin tưởng mua sản phẩm, đơn hàng sẽ nhanh chóng đến tay của bạn.<br />Bạn có thể xem chi tiết đơn hàng trong mục Đơn hàng của tôi. </h4><br />Xin chân thành cảm ơn bạn !!! Rất vui được phục vụ.")));

        try {
            response = client.post(request);
        } catch (MailjetException | MailjetSocketTimeoutException e) {
            return;
        }
    }
}
