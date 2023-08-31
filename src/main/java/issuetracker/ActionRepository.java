package issuetracker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Long> {
    List<Action> findAllByIssueId(long issueId);
}
