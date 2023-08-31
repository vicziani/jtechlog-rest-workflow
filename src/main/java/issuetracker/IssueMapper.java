package issuetracker;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IssueMapper {

    IssueResource toResource(Issue issue);

    ActionResource toResource(Action action);

    void update(@MappingTarget Issue issue, IssueResource resource);
}
