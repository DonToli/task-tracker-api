package my.code.task.tracker.api.controllers;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my.code.task.tracker.api.controllers.helpers.ControllerHelper;
import my.code.task.tracker.api.dto.TaskStateDto;
import my.code.task.tracker.api.exceptions.BadRequestException;
import my.code.task.tracker.api.factories.TaskStateDtoFactory;
import my.code.task.tracker.store.entities.ProjectEntity;
import my.code.task.tracker.store.entities.TaskStateEntity;
import my.code.task.tracker.store.repositories.TaskStateRepository;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class TaskStateController {

    TaskStateRepository taskStateRepository;

    TaskStateDtoFactory taskStateDtoFactory;

    ControllerHelper controllerHelper;

    public static final String GET_TASK_STATES = "/api/projects/{project_id}/task-states";
    public static final String CREATE_TASK_STATES = "/api/projects/{project_id}/task-states";
    public static final String CREATE_OR_UPDATE_PROJECT = "/api/projects";
    public static final String DELETE_PROJECT = "/api/projects/{project_id}";

    @GetMapping(GET_TASK_STATES)
    public List<TaskStateDto> getTaskStates(@PathVariable(name = "project_id") Long projectId){

        ProjectEntity project = controllerHelper.getProjectOrThrowException(projectId);

        return project
                .getTaskState()
                .stream()
                .map(taskStateDtoFactory::makeTaskStateDto)
                .collect(Collectors.toList());
    }

    @PostMapping(CREATE_TASK_STATES)
    public TaskStateDto createTaskState(
                @PathVariable(name = "project_id") Long projectId,
                @RequestParam(name = "task_state_name") String taskStateName){

        ProjectEntity project = controllerHelper.getProjectOrThrowException(projectId);

        if (taskStateName.trim().isEmpty()){
            throw new BadRequestException("Task state namr can't be empty.");
        }

        project.getTaskState()
                .stream()
                .map(TaskStateEntity::getName)
                .filter(anotherTaskStatename -> anotherTaskStatename.equalsIgnoreCase(taskStateName))
                .findAny()
                .ifPresent(it ->{
                    throw new BadRequestException(String.format("Task state \"%s\" already exists.",taskStateName));
                });

        TaskStateEntity taskState = taskStateRepository.saveAndFlush(
                        TaskStateEntity
                                .builder()
                                .name(taskStateName)
                                .build()
        );

        return new TaskStateDto();

    }

}
