package models.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_roles",uniqueConstraints =
@UniqueConstraint(columnNames = {"roleId","userId"}))
public class UserRole {
    @Id
    @Column(name = "userRoleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userRoleId;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private int roleId;

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
