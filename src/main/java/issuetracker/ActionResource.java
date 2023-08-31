package issuetracker;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ActionResource {

    Long id;

    ActionType type;

    LocalDateTime createdAt;

}
