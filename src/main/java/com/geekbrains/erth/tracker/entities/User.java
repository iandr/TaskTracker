package com.geekbrains.erth.tracker.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_user_id")
    @SequenceGenerator(name = "s_user_id", sequenceName = "s_user_id", allocationSize = 1)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Task> ownerTasks;

    @OneToMany(mappedBy = "executor")
    @JsonBackReference
    private List<Task> executorTasks;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return id.hashCode() + name.hashCode();
    }
}
