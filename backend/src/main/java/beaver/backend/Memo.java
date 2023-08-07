package beaver.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_memo")
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String memoTextselet;
}