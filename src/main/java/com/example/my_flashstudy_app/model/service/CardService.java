package com.example.my_flashstudy_app.model.service;

import com.example.my_flashstudy_app.model.entity.Card;
import com.example.my_flashstudy_app.model.entity.User;
import com.example.my_flashstudy_app.model.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepo;

//-------------------------------------------------------------------
    public List<Card> getAll() {
        return cardRepo.findAll();
    }
    public List<Card> getByUser(User user) {
        return cardRepo.findByUser(user);
    }
//-------------------------------------------------------------------

    public void save(Card card) {
        cardRepo.save(card);
    }

//-------------------------------------------------------------------
    public void deleteById(Integer id) {
        cardRepo.deleteById(id);
    }
    public Card findById(Integer id) {
        return cardRepo.findById(id).orElse(null);
    }

}
