package my.code.task.tracker.store.repositories;

import my.code.task.tracker.store.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
}
