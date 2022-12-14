package models.entities;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "mail_confirm_tokens")
@Entity
public class MailConfirmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String verifyToken;

    @Column(nullable = false)
    private LocalDateTime dateExpired;
    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public LocalDateTime getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(LocalDateTime dateExpired) {
        this.dateExpired = dateExpired;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
