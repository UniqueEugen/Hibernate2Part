package org.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String role;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public User(String login, String password, String user) {
        this.login = login;
        this.password = password;
        this.role=user;
    }
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
