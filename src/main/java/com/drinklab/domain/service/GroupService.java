package com.drinklab.domain.service;

import com.drinklab.api.exceptions.customExceptions.NotFoundException;
import com.drinklab.domain.model.Group;
import com.drinklab.domain.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group findById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Grupo de ID %d, não foi encontrado", id)));
    }
}
