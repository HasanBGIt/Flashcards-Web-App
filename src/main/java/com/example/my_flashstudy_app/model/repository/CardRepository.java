package com.example.my_flashstudy_app.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.my_flashstudy_app.model.entity.Card;
import com.example.my_flashstudy_app.model.entity.User;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    List<Card> findByUser(User user);
}
