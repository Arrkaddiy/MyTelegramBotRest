package ru.league.telegram.entity;

import ru.league.telegram.model.UserModel;

import javax.persistence.*;

@Entity
@Table(name = "T_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long chatId;
    private Long tinderId;
    private String stateName;

    public User() {
    }

    public User(Long chatId) {
        this.chatId = chatId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getTinderId() {
        return tinderId;
    }

    public void setTinderId(Long tinderId) {
        this.tinderId = tinderId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public User updateByUserModel(UserModel userModel) {
        setTinderId(userModel.getTinderId());
        setStateName(userModel.getStateName());
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", tinderId=" + tinderId +
                ", stateName='" + stateName + '\'' +
                '}';
    }
}
