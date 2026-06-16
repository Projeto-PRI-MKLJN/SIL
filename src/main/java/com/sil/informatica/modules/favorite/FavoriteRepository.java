package com.sil.informatica.modules.favorite;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sil.informatica.modules.sign.Sign;
import com.sil.informatica.modules.user.User;

/// Repositório para abstração de persistência da entidade [Favorite].
///
/// Gerencia os vínculos de favoritos entre usuários e termos do glossário.
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    /// Lista todos os favoritos de um usuário específico.
    ///
    /// @param user O [User] proprietário dos favoritos.
    /// @return Uma lista de objetos [Favorite].
    List<Favorite> findByUser(User user);

    /// Busca um vínculo de favorito específico.
    ///
    /// @param user O usuário.
    /// @param sign O sinal favoritado.
    /// @return Um [Optional] contendo o registro se localizado.
    Optional<Favorite> findByUserAndSign(User user, Sign sign);

    /// Remove todos os favoritos vinculados a um sinal específico pelo ID do sinal.
    ///
    /// @param signId O ID do sinal a ter os favoritos removidos.
    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.sign.id = :signId")
    void deleteBySignId(@Param("signId") Long signId);

    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
