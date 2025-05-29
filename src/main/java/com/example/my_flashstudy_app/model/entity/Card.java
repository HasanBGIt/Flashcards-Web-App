package com.example.my_flashstudy_app.model.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Card name is required")
    @Size(max = 50, message = "Card name must be at most 50 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Category is required")
    @Size(max = 30, message = "Category must be at most 30 characters")
    private String category;

    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
//-------------------------------------------------------------------

    public Card() {
    }

    public Card(String name, String category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
    }


//-------------------------------------------------------------------

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


}
