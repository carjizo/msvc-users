package org.sisvir.msvc.users.services.impl;

import org.sisvir.msvc.users.models.entities.User;
import org.sisvir.msvc.users.persistence.UserDAO;
import org.sisvir.msvc.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDAO.findById(id);
    }

    @Override
    public void create(User user) {
        userDAO.create(user);
    }

    @Override
    public void deleteById(Long id) {
        userDAO.deleteById(id);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userDAO.findByUserName(userName);
    }
}
