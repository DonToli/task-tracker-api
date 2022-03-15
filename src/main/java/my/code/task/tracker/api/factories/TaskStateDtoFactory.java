package my.code.task.tracker.api.factories;

import my.code.task.tracker.api.dto.TaskStateDto;
import my.code.task.tracker.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;


@Component
public class TaskStateDtoFactory {

    public TaskStateDto makeTaskStateDto(TaskStateEntity entity){

        return TaskStateDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreateAt())
                .ordinal(entity.getOrdinal())
                .build();

    }
}
