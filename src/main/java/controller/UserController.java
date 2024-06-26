package controller;

import dto.RequestUserDTO;
import dto.ResponseUserDTO;
import library.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<ResponseUserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userID}")
    public ResponseUserDTO getUserByID(@PathVariable long userID) {
        return userService.getUserByID(userID);
    }

    @PostMapping
    public void insertUser(@RequestBody User user) {
        userService.insertUser(user);
    }

    @PutMapping
    public void updateUser(@RequestBody RequestUserDTO requestUserDTO) {
        RequestUserDTO RequestUserDTO = null;
        userService.updateUser(RequestUserDTO);
    }

    @PatchMapping("/{userID}/lock")
    public void updateUserLOCK(@PathVariable long userID) {
        userService.updateUserLOCK(userID);
    }

    @PatchMapping("/{userID}/unlock")
    public void updateUserUNLOCK(@PathVariable long userID) {
        userService.updateUserUNLOCK(userID);
    }

    @PostMapping("/{userID}/rateBook/{bookID}")
    public void addUserRatingToBook(@PathVariable long userID, @PathVariable long bookID, @RequestParam double rating) {
        userService.addUserRatingToBook(userID, bookID, rating);
    }

    @DeleteMapping("/{userID}")
    public void deleteUser(@PathVariable long userID) {
        userService.deleteUser(userID);
    }
}
