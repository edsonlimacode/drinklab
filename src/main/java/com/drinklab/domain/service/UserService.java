package com.drinklab.domain.service;


import com.drinklab.api.exceptions.customExceptions.BadRequestException;
import com.drinklab.domain.model.User;
import com.drinklab.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void create(User user) {

        var userExists = this.getUserByEmail(user.getEmail());

        if (userExists) {
            throw new BadRequestException("Já existe um usuário cadastrado com o email: " + user.getEmail());
        }

        this.userRepository.save(user);
    }

    public boolean getUserByEmail(String email) {
        return userRepository.userExistsByEmail(email);
    }
}
