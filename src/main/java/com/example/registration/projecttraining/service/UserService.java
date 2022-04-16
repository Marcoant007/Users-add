package com.example.registration.projecttraining.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.example.registration.projecttraining.database.UserRepository;
import com.example.registration.projecttraining.dto.UserDTO;
import com.example.registration.projecttraining.entity.User;
import com.example.registration.projecttraining.service.exceptions.DatabaseException;
import com.example.registration.projecttraining.service.exceptions.ServiceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(PageRequest pageRequest) {
        Page<User> userList = userRepository.findAll(pageRequest);
        Page<UserDTO> userDTO = userList.map(userObject -> new UserDTO(userObject));
        return userDTO;
    }

    @Transactional(readOnly = true)
    public UserDTO getById(Long id) {
        Optional<User> userObject = userRepository.findById(id);
        User user = userObject.orElseThrow(() -> new ServiceNotFoundException("Entity not found"));
        return new UserDTO(user);
    }

    @Transactional()
    public UserDTO createUser(UserDTO userDTO) {
        User userEntity = new User();
        transformDTOInEntity(userDTO, userEntity);
        userEntity = userRepository.save(userEntity);
        return new UserDTO(userEntity);
    }

    @Transactional()
    public UserDTO updatedUser(Long id, UserDTO userDTO) {
        try {
            User userEntity = userRepository.getOne(id);
            transformDTOInEntity(userDTO, userEntity);
            userEntity = userRepository.save(userEntity);
            return new UserDTO(userEntity);
        } catch (EntityNotFoundException e) {
            throw new ServiceNotFoundException("Id not Found " + id);
        }
    }

    public void deletedUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ServiceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void transformDTOInEntity(UserDTO userDTO, User userEntity) {
        userEntity.setName(userDTO.getName());
        userEntity.setCpf(userDTO.getCpf());
        userEntity.setIncome(userDTO.getIncome());
        userEntity.setBirthDate(userDTO.getBirthDate());
        userEntity.setChildren(userDTO.getChildren());
    }
}
