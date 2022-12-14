package models.repositories.mail_verified_token;

public interface IVerifyTokenRepository {
    int insertToken(int userId);
    int deleteToken(String token);
    String verifyToken(String token);
    String getVerifyTokenById(int verifyTokenId);
}
