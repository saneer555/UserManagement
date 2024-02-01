package insurenceMain.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import insurenceMain.Binding.ActivateAccount;
import insurenceMain.Binding.Login;
import insurenceMain.Binding.User;
import insurenceMain.Services.UserServices;

@RestController
public class UserController {

    @Autowired
    private UserServices userServices;

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody User user) {
        Boolean register = userServices.register(user);
        if (register) {
            return new ResponseEntity<>("Registration Success", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Registration Fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activate(@RequestBody ActivateAccount active) {
        Boolean account = userServices.activeAccount(active);
        if (account) {
            return new ResponseEntity<>("Account Activate Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Account Not Activate Success", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login signIn) {
        String userLogin = userServices.userLogin(signIn);
        return new ResponseEntity<>(userLogin, HttpStatus.OK);
    }

    @PostMapping("/forgotpassword/{userEmail}")
    public ResponseEntity<String> forgotPassword(@PathVariable String userEmail) {
        String forgotPassword = userServices.forgotPassword(userEmail);
        return new ResponseEntity<>(forgotPassword, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll() {
        List<User> list = userServices.getAllUsers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> upsertUser(@RequestBody User user, @PathVariable Integer id) {
        Boolean user2 = userServices.updateUser(id, user);
        if (user2) {
            return new ResponseEntity<>("Update Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Update fail", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        Boolean user = userServices.deleteUser(id);
        if (user) {
            return new ResponseEntity<>("Delete Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Deleted UnSuccess", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/softdelete/{status}/{id}")
    public ResponseEntity<String> softDelete(@PathVariable String status, @PathVariable Integer id) {
        Boolean delete = userServices.softDelete(id, status);
        if (delete) {
            return new ResponseEntity<>("Account changed Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Account not Change", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id) {
        User userById = userServices.getUserById(id);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }
}
