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
import java.util.Objects;
import java.util.Optional;
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
    public static final String UPDATE_TASK_STATES = "/api/task-states/{task_state_id}";
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

        if (taskStateName.trim().isEmpty()){
            throw new BadRequestException("Task state name can't be empty.");
        }

        ProjectEntity project = controllerHelper.getProjectOrThrowException(projectId);

        Optional<TaskStateEntity> optionalAnotherTaskState = Optional.empty();

        for (TaskStateEntity taskState: project.getTaskState()){

            if (taskState.getName().equalsIgnoreCase(taskStateName)){
                throw new BadRequestException(String.format("Task state \"%s\" already exists.",taskStateName));
            }

            if (!taskState.getRightTaskState().isPresent()){
                optionalAnotherTaskState = Optional.of(taskState);
                break;
            }

        }

         TaskStateEntity taskState = taskStateRepository.saveAndFlush(
                 TaskStateEntity.builder()
                                .name(taskStateName)
                                .project(project)
                                .build()
         );


        optionalAnotherTaskState.ifPresent(anotherTaskState -> {
                     taskState.setLeftTaskState(anotherTaskState);

                     anotherTaskState.setRightTaskState(taskState);

                     taskStateRepository.saveAndFlush(anotherTaskState);
                 } );

         final TaskStateEntity savedTaskState = taskStateRepository.saveAndFlush(taskState);

        return taskStateDtoFactory.makeTaskStateDto(savedTaskState);

    }

    @PostMapping(UPDATE_TASK_STATES)
    public TaskStateDto updateTaskState(
            @PathVariable(name = "task_state_id") Long taskStateId,
            @RequestParam(name = "task_state_name") String taskStateName) {

        if (taskStateName.trim().isEmpty()){
            throw new BadRequestException("Task state name can't be empty.");
        }
return null;
    }

}
