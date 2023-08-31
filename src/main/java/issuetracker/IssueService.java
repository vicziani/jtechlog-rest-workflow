package issuetracker;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class IssueService {

    private IssueRepository issueRepository;

    private ActionRepository actionRepository;

    private IssueMapper issueMapper;

    public List<IssueResource> getIssues() {
        return issueRepository.findAll().stream().map(issue -> issueMapper.toResource(issue)).toList();
    }

    public IssueResource createIssue(IssueResource resource) {
        var issue = Issue.createFromResource(resource);
        issueRepository.save(issue);
        return issueMapper.toResource(issue);
    }

    @Transactional
    public ActionResource createAction(long issueId, ActionResource actionResource) {
        var issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Can not find issue by id %d".formatted(issueId)));
        var action = Action.createFromResource(actionResource, issueId);
        actionRepository.save(action);
        switch (action.getType()) {
            case START_WORK -> issue.startWork();
            case COMPLETE_WORK -> issue.completeWork();
            default -> throw new IllegalArgumentException("Invalid action");
        }
        return issueMapper.toResource(action);
    }

    public IssueResource findIssueById(long id) {
        return issueRepository
                .findById(id)
                .map(issue -> issueMapper.toResource(issue))
                .orElseThrow(() -> new IllegalArgumentException("Can not find issue by id %d".formatted(id)));
    }

    @Transactional
    public IssueResource update(long id, IssueResource patchedIssue) {
        var issue = issueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Can not find issue by id %d".formatted(id)));
        issueMapper.update(issue, patchedIssue);
        return issueMapper.toResource(issue);
    }

    public List<ActionResource> findActionsForIssue(long issueId) {
        return actionRepository
                .findAllByIssueId(issueId)
                .stream()
                .map(action -> issueMapper.toResource(action))
                .toList();
    }
}
