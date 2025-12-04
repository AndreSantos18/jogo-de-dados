package com.lunaltas.dicegame.domain;

import jakarta.persistence.*;
import java.util.*;
import com.lunaltas.dicegame.domain.User;


@Entity
public class Bet extends AbstractEntity<Long> {

    private String title;
    private String description;
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private User winner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "bet_participants",
            joinColumns = @JoinColumn(name = "bet_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> participants = new ArrayList<>();

    // Getters e Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public User getWinner() { return winner; }
    public void setWinner(User winner) { this.winner = winner; }

    public List<User> getParticipants() { return participants; }
    public void setParticipants(List<User> participants) { this.participants = participants; }

    public String getUsername() {
    return this.username; 
  }
    public void setUsername(String username){
      this.username = username;
    }
}
