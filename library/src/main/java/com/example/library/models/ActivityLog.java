import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ActivityLog")
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String action;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp = new Date();

    // Getters i Setters
}
