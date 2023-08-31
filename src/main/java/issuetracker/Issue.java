package issuetracker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;

    private IssueState state;

    public Issue(String title) {
        this.title = title;
        this.state = IssueState.NEW;
    }

    public static Issue createFromResource(IssueResource resource) {
        if (resource.getId() != null) {
            throw new IllegalArgumentException("Can not specify id");
        }
        if (resource.getState() != null) {
            throw new IllegalArgumentException("Can not specify state");
        }
        return new Issue(resource.getTitle());
    }

    public void startWork() {
        state = state.startWork();
    }

    public void completeWork() {
        state = state.completeWork();
    }
}
