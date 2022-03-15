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
@Table(name = "project")
@Generated
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(unique = true)
    String name;

    @Builder.Default
    Instant updateAt =Instant.now();

    @Builder.Default
    Instant createAt =Instant.now();

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "project_id",referencedColumnName = "id")
    List<TaskStateEntity> taskState = new ArrayList<>();

}
/*
Hibernate: alter table if exists task_state add column project_id int8
Hibernate: alter table if exists task_state add constraint FK8im2k55lespj2c1o8prhogced foreign key (project_id) references project

 Hibernate: alter table if exists task_state_tasks add constraint UK_fl0d5rcflfapvyt57qfxps3e6 unique (tasks_id)
Hibernate: alter table if exists task_state add constraint FK8im2k55lespj2c1o8prhogced foreign key (project_id) references project
Hibernate: alter table if exists task_state_tasks add constraint FKdbxfi5mdk2n7g9h5c81o5l0x3 foreign key (tasks_id) references task
Hibernate: alter table if exists task_state_tasks add constraint FKaw778y6kuk3tjdl92e8gc6ci7 foreign key (task_state_entity_id) references task_state

 Hibernate: alter table if exists task_state add constraint UK_ip9mpmo57g08xx45gtlrff4ky unique (name)
Hibernate: alter table if exists task add constraint FKg9hk1uvo6tnmjpgm2cdosnm8j foreign key (task_state_id) references task_state
Hibernate: alter table if exists task_state add constraint FK8im2k55lespj2c1o8prhogced foreign key (project_id) references project


 */