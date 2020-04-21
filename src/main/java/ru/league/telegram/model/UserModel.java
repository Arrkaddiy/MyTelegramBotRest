package ru.league.telegram.model;

public class UserModel {

    private Long tinderId;
    private String stateName;
    private String response;

    public UserModel() {
    }

    public UserModel(Long tinderId, String stateName, String response) {
        this.tinderId = tinderId;
        this.stateName = stateName;
        this.response = response;
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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "tinderId=" + tinderId +
                ", stateName='" + stateName + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
