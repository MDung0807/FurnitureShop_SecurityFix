package models.entities;

import javax.persistence.*;
import java.util.List;

@Table(name = "wish_lists")
@Entity
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishListId;
    @OneToMany
    @JoinColumn(name = "wishId")
    private List<WishItem> wishItems;
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public int getWishListId() {
        return wishListId;
    }

    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

    public List<WishItem> getWishItems() {
        return wishItems;
    }

    public void setWishItems(List<WishItem> wishItems) {
        this.wishItems = wishItems;
    }
}
