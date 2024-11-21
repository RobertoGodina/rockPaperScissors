package com.rps.rockPaperScissors.domain;

import com.rps.rockPaperScissors.domain.game.GameResult;
import com.rps.rockPaperScissors.domain.game.Move;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "gameHistory")
public class GameHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private UserDB user;

    @Column(nullable = false)
    private Move userMove;

    @Column(nullable = false)
    private Move computerMove;

    @Column(nullable = false)
    private GameResult result;

    @Column(name = "modification_date")
    @CreationTimestamp
    private LocalDateTime playedAt;

}
