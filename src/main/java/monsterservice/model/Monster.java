package monsterservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity //ไปทำการต่อกับตัว data
@Table(name = "monsters")
@Getter
@Setter
public class Monster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto id
    private Integer id;
    private String name;
    private Integer health;
}
