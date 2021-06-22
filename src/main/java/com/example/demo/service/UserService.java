package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String search(String userID, Model model, HttpServletRequest req){
        //validation
        if(userID.length() <= 0){
            return "redirect:/?error=1";
        }

        HttpSession session = req.getSession();
        Optional<User> user = userRepository.findById(Integer.parseInt(userID));
        if(user.isEmpty()){
            return "redirect:/?error=2";
        }
        // add the user in the session so its easy to update his information between pages without query everytime
        user.ifPresent(user1 -> model.addAttribute("user", user1));
        user.ifPresent(user1 -> session.setAttribute("user", user1));

        return "result";
    }

    public String edit(String firstName, String lastName, HttpServletRequest req){
        HttpSession session = req.getSession();
        if(session.getAttribute("user") == null){
            return "redirect:/";
        }

        // get the user from the session
        User user = (User) session.getAttribute("user");

        // validation before updating
        if(firstName.length() > 0){
            user.setFirstName(firstName);
        }
        if(lastName.length() > 0){
            user.setLastName(lastName);
        }

        userRepository.save(user);
        return "confirmation";
    }
}