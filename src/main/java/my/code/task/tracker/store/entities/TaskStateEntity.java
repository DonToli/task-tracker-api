package my.code.task.tracker.store.entities;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Entity
@Table(name = "task_state")
public class TaskStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(unique = true)
    String name;

    Long ordinal;

    @Builder.Default
    Instant createAt = Instant.now();

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "task_state_id", referencedColumnName = "id")
    List<TaskEntity> tasks = new ArrayList<>();

}
