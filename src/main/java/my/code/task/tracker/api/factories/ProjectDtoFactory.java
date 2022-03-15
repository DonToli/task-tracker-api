package my.code.task.tracker.api.factories;

import my.code.task.tracker.api.dto.ProjectDto;
import my.code.task.tracker.store.entities.ProjectEntity;
import org.springframework.stereotype.Component;


@Component
public class ProjectDtoFactory {

    public ProjectDto makeProjectDto(ProjectEntity entity){

        return ProjectDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreateAt())
                .updatedAt(entity.getUpdateAt())
                .build();

    }
}
