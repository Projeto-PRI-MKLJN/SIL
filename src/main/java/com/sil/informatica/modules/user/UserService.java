package com.sil.informatica.modules.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/// Serviço responsável pelo gerenciamento de usuários e validação de regras de domínio.
///
/// Este serviço assegura a integridade dos dados de [User] e controle de acesso básico.
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /// Salva um novo usuário no sistema, validando a unicidade do e-mail.
    ///
    /// @param user O objeto [User] com os dados do novo usuário.
    /// @return O usuário persistido com sucesso.
    /// @throws RuntimeException Caso o e-mail já esteja em uso.
    public User save(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(existingUser -> {
            if (user.getId() == null || !existingUser.getId().equals(user.getId())) {
                throw new RuntimeException("E-mail já cadastrado.");
            }
        });

        // Criptografa a senha antes de salvar, se ela estiver presente
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            if (user.getPassword().length() < 6) {
                throw new RuntimeException("A senha deve ter no mínimo 6 caracteres.");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else if (user.getId() != null) {
            // Se for edição e a senha estiver em branco, mantém a senha atual
            userRepository.findById(user.getId()).ifPresent(existing -> {
                user.setPassword(existing.getPassword());
            });
        } else {
            throw new RuntimeException("A senha é obrigatória para novos usuários.");
        }
        
        return userRepository.save(user);
    }

    /// Localiza um usuário no banco de dados através do e-mail informado.
    ///
    /// @param email O e-mail para busca.
    /// @return Um [Optional] contendo o [User] se localizado.
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /// Recupera um usuário pelo seu identificador primário.
    ///
    /// @param id O ID do usuário.
    /// @return Um [Optional] contendo o [User] se localizado.
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /// Recupera todos os usuários cadastrados.
    ///
    /// @return Lista de todos os usuários.
    public java.util.List<User> findAll() {
        return userRepository.findAll();
    }

    /// Remove um usuário pelo seu ID.
    ///
    /// @param id O ID do usuário a ser removido.
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}

