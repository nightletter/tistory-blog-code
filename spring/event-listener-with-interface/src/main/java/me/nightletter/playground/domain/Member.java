package me.nightletter.playground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.expression.spel.ast.Identifier;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    @Setter
    private String username;

    public Member(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }
}
