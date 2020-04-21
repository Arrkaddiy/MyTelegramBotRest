package ru.league.telegram.model;

public class ProfileModel {

    private Long id;
    private String name;
    private String about;

    public ProfileModel() {
    }

    public ProfileModel(Long id, String name, String about) {
        this.id = id;
        this.name = name;
        this.about = about;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "ProfileModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", about='" + about + '\'' +
                '}';
    }

    public String getProfile() {
        return this.name + ":\n"
                + this.about;
    }
}
