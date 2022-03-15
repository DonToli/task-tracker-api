package my.code.task.tracker.api.controllers;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my.code.task.tracker.api.dto.ProjectDto;
import my.code.task.tracker.api.exceptions.BadRequestException;
import my.code.task.tracker.api.factories.ProjectDtoFactory;
import my.code.task.tracker.store.repositories.ProjectRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class ProjectController {

    ProjectRepository projectRepository;

    ProjectDtoFactory projectDtoFactory;

    public static final String CREATE_PROJECT = "/api/projects";

    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject (@RequestParam String name){

        projectRepository.findByName(name)
                .ifPresent(project -> {

                    throw new BadRequestException(String.format("Project \"%s\" already exists.", name));

                });

        //TODO: uncomit and insert entity in mrthod

//        return projectDtoFactory.makeProjectDto();
        return null;
    }

}
