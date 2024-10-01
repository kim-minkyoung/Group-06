package com.example.rl_simulation_wep.service;

import com.example.rl_simulation_wep.entity.Gender;
import com.example.rl_simulation_wep.entity.User;
import com.example.rl_simulation_wep.repository.UserRepository;
import com.example.rl_simulation_wep.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO createUser(UserDTO userDTO, String rawPassword) { // 비밀번호는 별도로 받아 처리
        User user = convertToEntity(userDTO);
        user.setUserPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);
        return convertToDTO(user);
    }

    public UserDTO getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(this::convertToDTO).orElse(null);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setUserName(user.getUserName());
        dto.setUserImage(user.getUserImage());
        dto.setUserGender(user.getUserGender().name());
        dto.setBirthDate(user.getBirthDate());
        dto.setUserBio(user.getUserBio());
        dto.setUserScore(user.getUserScore());
        return dto; // 비밀번호는 포함하지 않음
    }

    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUserName(dto.getUserName());
        user.setUserImage(dto.getUserImage());
        user.setUserGender(Gender.valueOf(dto.getUserGender()));
        user.setBirthDate(dto.getBirthDate());
        user.setUserBio(dto.getUserBio());
        return user;
    }
}
