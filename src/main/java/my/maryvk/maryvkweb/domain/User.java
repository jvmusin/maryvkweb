package my.maryvk.maryvkweb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class User {
    @Id
    private Integer id;

    private String firstName;
    private String lastName;

    public String link() {
        return "http://vk.com/id" + id;
    }

    public String fullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return String.format("%d: %s %s", id, firstName, lastName);
    }
}