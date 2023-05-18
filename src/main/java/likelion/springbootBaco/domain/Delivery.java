package likelion.springbootBaco.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Delivery {
    @Id @GeneratedValue
    private Long id;

    private DeliveryStatus deliveryStatus;

    private String city;
    private String state;
    private String street;
    private String zipcode;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    public enum DeliveryStatus {
        ESTABLISHED, PROGRESS, DONE
    }
}
