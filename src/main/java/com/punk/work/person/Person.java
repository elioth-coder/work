package com.punk.work.person;

import java.util.List;

import com.punk.work.task.Task;
import com.punk.work.task.TaskService;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.AccessLevel;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String role;

    @OneToMany(mappedBy = "task")
    private List<Task> tasks;
}
