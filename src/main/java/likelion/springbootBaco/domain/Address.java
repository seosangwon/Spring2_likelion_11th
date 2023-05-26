package likelion.springbootBaco.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable // 내장 타입임을 나타냄
@Data // 롬복 어노테이션, getter,setter 등등 사용가능
@AllArgsConstructor // 롬복 어노테이션, 필드내 선언되어 있는 인자를 모두 받는 생성자 생성
@NoArgsConstructor // 롬복 어노테이션 , 인자를 받지 않는 기본 생성자 생성
public class Address {
    private String city;
    private String state;
    private String street;
    private String zipcode;
}
