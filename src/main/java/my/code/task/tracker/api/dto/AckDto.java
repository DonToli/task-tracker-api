package my.code.task.tracker.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AckDto {

    Boolean answer;

    public static AckDto maeDefault(Boolean answer){
        return  builder()
                .answer(answer)
                .build();
    }
}
