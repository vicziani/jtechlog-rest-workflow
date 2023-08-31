package issuetracker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long issueId;

    private ActionType type;

    private LocalDateTime createdAt;

    public Action(Long issueId, ActionType type) {
        this.issueId = issueId;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }

    public static Action createFromResource(ActionResource resource, long issueId) {
        if (resource.getId() != null) {
            throw new IllegalArgumentException("Can not specify id");
        }
        return new Action(issueId, resource.getType());
    }
}
