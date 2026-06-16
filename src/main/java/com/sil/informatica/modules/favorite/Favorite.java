package com.sil.informatica.modules.favorite;

import com.sil.informatica.modules.sign.Sign;
import com.sil.informatica.modules.user.User;
import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/// Representa a associação de "favorito" entre um usuário e um sinal técnico.
///
/// Esta entidade é fundamental para a personalização da experiência do [User] dentro do glossário.
@Entity
@Table(name = "favorites")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "sign_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Sign sign;

    /// Construtor padrão para o provedor de persistência.
    public Favorite() {
    }

    /// Cria uma nova associação de favorito.
    ///
    /// @param user O [User] que favoritou o sinal.
    /// @param sign O [Sign] que foi favoritado.
    public Favorite(User user, Sign sign) {
        this.user = user;
        this.sign = sign;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }
}
