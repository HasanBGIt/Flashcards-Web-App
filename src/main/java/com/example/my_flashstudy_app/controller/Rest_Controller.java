package com.example.my_flashstudy_app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_flashstudy_app.model.entity.Card;
import com.example.my_flashstudy_app.model.entity.User;
import com.example.my_flashstudy_app.model.service.CardService;
import com.example.my_flashstudy_app.model.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest")
public class Rest_Controller {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    @GetMapping("/cards/all")
    public ResponseEntity<?> getAllCards() {
        return ResponseEntity.ok(cardService.getAll());
    }

    @GetMapping("/users/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    //-------------------------------------------------------------------
    
    @GetMapping("/user/{Id}")
    public ResponseEntity<?> getCardsByUser(@PathVariable Integer Id) {
        User user = userService.findById(Id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found in the database");
        } else {
            List<Card> cards = cardService.getByUser(user);
            return ResponseEntity.ok(cards);
        }
    }

    //-------------------------------------------------------------------

    @PostMapping("/cards/create")
    public ResponseEntity<?> createCard(@Valid @RequestBody Card card, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.joining(", "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid card data: " + errorMessage);
        }
        cardService.save(card);
        return ResponseEntity.ok(card);
}

     //-------------------------------------------------------------------

    @DeleteMapping("/cards/delete/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Integer id) {
        Card existing = cardService.findById(id);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found.");
        }
        cardService.deleteById(id);
        return ResponseEntity.ok("Card deleted successfully.");
    }




}
