package my.maryvk.maryvkweb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class RelationChange {

    @Id @GeneratedValue
    private Long id;

    private LocalDateTime time;
    private Integer ownerId;
    private Integer targetId;

    private RelationType relationType;
    private Boolean isAppeared;
}