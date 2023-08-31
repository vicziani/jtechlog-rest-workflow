package issuetracker;

import lombok.Value;

@Value
public class IssueResource {

    private Long id;

    private String title;

    private IssueState state;

}
