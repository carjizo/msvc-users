package org.sisvir.msvc.users.persistence;

import org.sisvir.msvc.users.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    List<User> findAll();

    Optional<User> findById(Long id);

    void create(User user);

    void deleteById(Long id);

    Optional<User> findByUserName(String userName);
}
