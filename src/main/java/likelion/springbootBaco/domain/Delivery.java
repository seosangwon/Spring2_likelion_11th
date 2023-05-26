package likelion.springbootBaco.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static likelion.springbootBaco.domain.Delivery.DeliveryStatus.ESTABLISHED;
import static lombok.AccessLevel.PROTECTED;

@Entity // 컴포넌트 스캔 Entity
@NoArgsConstructor(access = PROTECTED) // 롬복 어노테이션 , 인자를 받지 않는 기본 생성자 생성 . 생성자의 접근수준은 상속관계만!
@Getter // 롬복 어노테이션 : getter
public class Delivery {
    @Id @GeneratedValue // pk지정, pk번호 자동 증가
    private Long id;

    @OneToOne(mappedBy = "delivery") // Order와 Delivery의 관계는 1:1 , Order클래스의 delivery필드에 의해 관리당함
    private Order order;

    @Enumerated(STRING) // @열거형 클래스를 안만들어도 됨, deliveryStatus가 열거형임을 나타냄
    private DeliveryStatus deliveryStatus;

    private String city;
    private String state;
    private String street;
    private String zipcode;

    // public static mehtod 를 통해 delviery 객체를 생성
    public static Delivery createDelivery(Order order, String city, String state, String street, String zipcode) {
        Delivery delivery = new Delivery();
        delivery.order = order;
        delivery.deliveryStatus = ESTABLISHED;
        delivery.city = city;
        delivery.state = state;
        delivery.street = street;
        delivery.zipcode = zipcode;
        return delivery;
    }

    //Enum 타입의 deliveryStatus
    public enum DeliveryStatus {
        ESTABLISHED, PROGRESS, DONE
    }
}
