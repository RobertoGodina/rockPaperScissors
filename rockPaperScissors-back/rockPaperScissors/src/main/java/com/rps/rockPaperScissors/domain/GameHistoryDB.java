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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "gameHistory")
public class GameHistoryDB {

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

    @Column(name = "played_at")
    @CreationTimestamp
    private LocalDateTime playedAt;

}
