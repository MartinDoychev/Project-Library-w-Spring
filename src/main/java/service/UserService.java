package service;

import library.User;
import dto.RequestUserDTO;
import dto.ResponseUserDTO;
import repository.UserRepository;
import enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<ResponseUserDTO> getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        return users.stream()
                .map(this::convertToResponseUserDTO)
                .collect(Collectors.toList());
    }

    public ResponseUserDTO getUserByID(long userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User with ID: " + userId + " does not exist!");
        }
        return convertToResponseUserDTO(user);
    }

    public void insertUser(User user) {
        userRepository.createUser(user);
    }

    public void updateUser(RequestUserDTO requestUserDTO) {
        User user = userRepository.getUserByUsername(requestUserDTO.getUserName());
        if (user == null) {
            throw new RuntimeException("User with username: " + requestUserDTO.getUserName() + " does not exist!");
        }
        updateUserFromDTO(user, requestUserDTO);
        userRepository.updateUser(user);
    }

    public void updateUserLOCK(long userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User with ID: " + userId + " does not exist!");
        }
        user.setLocked(true);
        userRepository.updateUser(user);
    }

    public void updateUserUNLOCK(long userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User with ID: " + userId + " does not exist!");
        }
        user.setLocked(false);
        userRepository.updateUser(user);
    }

    public void addUserRatingToBook(long userID, long bookID, double rating) {
        // Implement the method to add user rating to book
    }

    private ResponseUserDTO convertToResponseUserDTO(User user) {
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setId(user.getId());
        responseUserDTO.setFirstName(user.getFirstName());
        responseUserDTO.setLastName(user.getLastName());
        responseUserDTO.setEmail(user.getEmail());
        responseUserDTO.setPhoneNumber(user.getPhoneNumber());
        responseUserDTO.setIsLocked(user.isLocked());
        responseUserDTO.setRole(user.getRole());
        responseUserDTO.setUserName(user.getUserName());
        return responseUserDTO;
    }

    private void updateUserFromDTO(User user, RequestUserDTO requestUserDTO) {
        user.setFirstName(requestUserDTO.getFirstName());
        user.setLastName(requestUserDTO.getLastName());
        user.setEmail(requestUserDTO.getEmail());
        user.setPhoneNumber(requestUserDTO.getPhoneNumber());
        user.setIsLocked(requestUserDTO.getIsLocked());
        user.setRole(requestUserDTO.getRole());
        user.setUserName(requestUserDTO.getUserName());
        user.setPassword(requestUserDTO.getPassword());
    }

    public void deleteUser(long userID) {
    }
}
