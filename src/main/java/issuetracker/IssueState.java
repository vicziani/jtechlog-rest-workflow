package issuetracker;

public enum IssueState {

    NEW, IN_PROGRESS, RESOLVED;

    public IssueState startWork() {
        if (this != NEW) {
            throw new IllegalArgumentException("You can start only new issue");
        }
        return IN_PROGRESS;
    }

    public IssueState completeWork() {
        if (this != IN_PROGRESS) {
            throw new IllegalArgumentException("You can start complete only in progress issue");
        }
        return RESOLVED;
    }

}
