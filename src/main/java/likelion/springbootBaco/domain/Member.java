package likelion.springbootBaco.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Member {
    @Id @GeneratedValue
    private Long id;

    private String name;

    private String city;
    private String state;
    private String street;
    private String zipcode;

}
