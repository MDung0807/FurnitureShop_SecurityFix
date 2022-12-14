package models.repositories.mail_verified_token;

import models.entities.MailConfirmToken;
import models.entities.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.DateUtils;
import utils.HibernateUtils;
import utils.constants.EMAIL_VERIFY_TOKEN_EXPIRATION;
import utils.constants.USER_STATUS;

import java.util.UUID;

public class VerifyTokenRepository implements IVerifyTokenRepository{

    private static VerifyTokenRepository instance = null;
    public static VerifyTokenRepository getInstance(){
        if(instance == null)
            instance = new VerifyTokenRepository();
        return instance;
    }
    @Override
    public int insertToken(int userId) {
        Session session = HibernateUtils.getSession();

        int verifyTokenId = -1;
        Transaction tx = null;

        MailConfirmToken token = new MailConfirmToken();
        token.setUser(session.find(User.class, userId));
        token.setVerifyToken(UUID.randomUUID().toString());
        token.setDateExpired(DateUtils.dateTimeNow().plusMinutes(EMAIL_VERIFY_TOKEN_EXPIRATION.EXPIRED));
        try{
            tx = session.beginTransaction();
            session.persist(token);
            tx.commit();
            verifyTokenId = token.getId();
        }catch (Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        session.close();
        return verifyTokenId;
    }

    @Override
    public int deleteToken(String token) {
        Session session = HibernateUtils.getSession();
        Query q = session.createQuery("from MailConfirmToken where verifyToken=:s1");
        q.setParameter("s1", token);
        Transaction tx = null;
        int userId = -1;
        try{
            MailConfirmToken confirmToken = (MailConfirmToken)q.getSingleResult();

            tx = session.beginTransaction();
            session.remove(confirmToken);
            tx.commit();
            userId = confirmToken.getUser().getUserId();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            session.close();
            return -1;
        }
        finally {
            session.close();
        }
        return userId;
    }

    @Override
    public String verifyToken(String token) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        Query q = session.createQuery("from MailConfirmToken where verifyToken=:s1");
        q.setParameter("s1", token);
        try{
            MailConfirmToken confirmToken = (MailConfirmToken)q.getSingleResult();
            if(DateUtils.dateTimeNow().isAfter(confirmToken.getDateExpired())) {
                session.close();
                return "expired";
            }
            User user = session.find(User.class, confirmToken.getUser().getUserId());
            user.setStatus(USER_STATUS.ACTIVE);

            tx = session.beginTransaction();
            session.merge(user);
            tx.commit();
        }catch (Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
            session.close();
            return "error";
        }
        session.close();
        return "success";
    }

    @Override
    public String getVerifyTokenById(int verifyTokenId) {
        Session session = HibernateUtils.getSession();
        MailConfirmToken confirmToken = session.find(MailConfirmToken.class, verifyTokenId);
        session.close();
        if(confirmToken == null)
            return "";
        return confirmToken.getVerifyToken();
    }
}
