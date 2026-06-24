package com.sil.informatica.modules.sign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/// Repositório para abstração de persistência da entidade [Sign].
///
/// Oferece métodos especializados para busca de termos técnicos usando Spring Data JPA.
@Repository
public interface SignRepository extends JpaRepository<Sign, Long> {
    /// Localiza todos os sinais cujo termo contenha a string informada (case-insensitive).
    ///
    /// @param term O texto para pesquisa do termo.
    /// @return Uma lista de [Sign] filtrados.
    List<Sign> findByTermContainingIgnoreCase(String term);
    List<Sign> findByTermStartingWithIgnoreCase(String letter);
    java.util.Optional<Sign> findByTermIgnoreCase(String term);
}
