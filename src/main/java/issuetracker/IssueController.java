package issuetracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/issues")
public class IssueController {

    private IssueService issueService;

    private ObjectMapper objectMapper;

    @GetMapping
    public List<IssueResource> getIssues() {
        return issueService.getIssues();
    }

    @GetMapping("{id}")
    public IssueResource getIssue(@PathVariable long id) {
        return issueService.findIssueById(id);
    }

    @PostMapping
    public IssueResource createIssue(@RequestBody IssueResource issueResource) {
        return issueService.createIssue(issueResource);
    }

    @GetMapping("/{issueId}/actions")
    public List<ActionResource> getIssueActions(@PathVariable long issueId) {
        return issueService.findActionsForIssue(issueId);
    }

    @PostMapping("/{issueId}/actions")
    public ActionResource createAction(@PathVariable long issueId, @RequestBody ActionResource actionResource) {
        return issueService.createAction(issueId, actionResource);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public IssueResource patchIssue(@PathVariable long id, @RequestBody JsonPatch patch) {
        var issue = issueService.findIssueById(id);
        var patched = patch(issue, patch);
        return issueService.update(id, patched);
    }

    private <T> T patch(T target, JsonPatch patch) {
        try {
            var json = objectMapper.convertValue(target, JsonNode.class);
            var patchedJson = patch.apply(json);
            return (T) objectMapper.treeToValue(patchedJson, target.getClass());
        }
        catch (JsonPatchException | JsonProcessingException e) {
            throw new IllegalArgumentException("Can not patch %s".formatted(target.getClass()), e);
        }
    }
}
