package my.maryvk.maryvkweb.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Relation implements Serializable {

    @Id @GeneratedValue
    private Long id;

    private Integer ownerId;
    private Integer targetId;
    private RelationType relationType;

    public RelationChange createRelationChange(boolean isAppeared) {
        return RelationChange.builder()
                .time(LocalDateTime.now())
                .ownerId(ownerId)
                .targetId(targetId)
                .relationType(relationType)
                .isAppeared(isAppeared)
                .build();
    }
}