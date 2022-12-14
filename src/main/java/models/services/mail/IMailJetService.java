package models.services.mail;

public interface IMailJetService {
    boolean sendMail(String name, String email, String content, String title);
}
