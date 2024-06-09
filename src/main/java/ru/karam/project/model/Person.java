package ru.karam.project.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class Person {
    private int person_id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 4, max = 100, message = "Длина ФИО должна быть от 5 до 50 символов")
    private String name;

    @Min(value = 10, message = "Your age should be more than 10 years!")
    private int age;

    public Person(){}
    public Person(int person_id, String name, int age) {
        this.person_id = person_id;
        this.name = name;
        this.age = age;
    }
    public int getPerson_id() {
        return person_id;
    }
    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
