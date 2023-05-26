package likelion.springbootBaco.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

/**
 * Entity 어노테이면 : Component Scan
 * Getter : Lombok : 유지 보수
 *@NoArgsConstructor(access = PROTECTED) : 파라미터가 없는 생성자 자동생성 :access=proteced로 설정해 상속관계에서만
 * 이 클래스를 사용 가능 -> 무분별한 인스턴스 생성 방지
 *
 *
 *
 * */

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {
    @Id @GeneratedValue // pk지정, pk번호는 생성시 자동으로 증가
    private Long id;

    private String name;

    // Member와 Order는 1:다 관계 (하나의 멤버는 여러개의 Order를 시킬 수 있다)
    // mappedby속성으로 주인을 지정 :  Order측에 member 라는 필드를 통해 이 관계가 관리 됨
    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>();

    //Address는 내장되어 있다 :  주소를 재사용하기 위해 주소 Entity가 그룹화 되어있다.
    //@Embedded 어노테이션을 통해  JPA에게 address가 내장되어 있다는 것을 알려준다
    @Embedded
    private Address address;

    //Static Factory Mehtod : Member 도메인 내에서 name,address를 받으면 Member를 생성해서 반환해주는 비즈니스 로직
   // public static이므로 member 객체가 생성 전에 이 static 메서드를 통하여 Member 객체를 만들 수 있다.
    //단일 Entity이므로 Member domain내에서 createMember 비즈니스 로직 생성
    public static Member createMember(String name, Address address) {
        Member member = new Member();
        member.name = name;
        member.address = address;
        return member;
    }
}
