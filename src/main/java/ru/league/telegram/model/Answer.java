package ru.league.telegram.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Answer {

    private Map<String, String> answer;

    public Answer(Map<String, String> answer) {
        this.answer = answer;
    }

    public Map<String, String> getAnswer() {
        return answer;
    }

    public void setAnswer(Map<String, String> answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answer=" + answer.get(0) +
                '}';
    }
}
