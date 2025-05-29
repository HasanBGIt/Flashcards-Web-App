package com.example.my_flashstudy_app.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.my_flashstudy_app.model.entity.Card;
import com.example.my_flashstudy_app.model.entity.User;
import com.example.my_flashstudy_app.model.service.CardService;
import com.example.my_flashstudy_app.model.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
@Controller
public class View_Controller {

    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;

    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //-------------------------------------------------------------------


    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login"; 
    }
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute User user, BindingResult bindingResult,Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        User foundUser = userService.findByUsername(user.getUsername()).orElse(null);
         
        if (foundUser != null) {
            if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                session.setAttribute("user", foundUser);
                return "redirect:/cards/list";
            } else {
                model.addAttribute("error", "Invalid password");
                return "login";
            }

        } else {
            userService.save(user);
            session.setAttribute("user", user);
            return "redirect:/cards/add";
        }
    }


//-------------------------------------------------------------------


    @GetMapping("/cards/add")
    public String showCardAdding(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        model.addAttribute("card", new Card());
        return "add";
    }

    @PostMapping("/cards/add")
    public String saveCard(@Valid @ModelAttribute Card card, BindingResult bindingResult, HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser == null) {
            return "redirect:/login";
        }
        if (bindingResult.hasErrors()) {
            return "add";
        }
        card.setUser(loggedUser);
        cardService.save(card);
        return "redirect:/cards/list";
    }


    //-------------------------------------------------------------------


    @GetMapping("/cards/update/{id}") 
    public String showUpdateForm(@PathVariable Integer id, Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser == null) {
            return "redirect:/login";
        }
        Card card = cardService.findById(id);
        if (card == null || !(card.getUser().getId().equals(loggedUser.getId()))) {
            return "redirect:/cards/list";
        }
        model.addAttribute("card", card);
        return "update";
    }

    @PostMapping("/cards/update/{id}")
    public String updateCard(@PathVariable Integer id, @Valid @ModelAttribute Card card, BindingResult bindingResult, HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser == null) {
            return "redirect:/login";
        }
        if (bindingResult.hasErrors()) {
            return "update";
        }
        Card existCard = cardService.findById(id);
        if (existCard == null || !(existCard.getUser().getId().equals(loggedUser.getId()))) {
            return "redirect:/cards/list";
        }
        existCard.setName(card.getName());
        existCard.setCategory(card.getCategory());
        existCard.setDescription(card.getDescription());
        cardService.save(existCard);
        return "redirect:/cards/list";
    }


    //-------------------------------------------------------------------


    @GetMapping("/cards/list")
    public String listCards(Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser == null) {
            return "redirect:/login";
        }
        List<Card> cards = cardService.getByUser(loggedUser);
        model.addAttribute("cards", cards);
        return "list";  
    }


    //-------------------------------------------------------------------
    
    @DeleteMapping("/cards/delete/{id}")
    public String deleteCard(@PathVariable Integer id) {
        cardService.deleteById(id);
        return "redirect:/cards/list";
    }

     //-------------------------------------------------------------------
    
    @GetMapping("/allCards")
    // @PreAuthorize("hasRole('ADMIN')")
    public String allCards(Model model) {
        List<Card> allCards = cardService.getAll();
        model.addAttribute("cards", allCards);
        return "list";
    }


}

