package likelion.springbootBaco.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "orders") // 이거 안하면 에러 , 테이블에 orders라는 이름으로 매핑됨
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Order {
    @Id
    @GeneratedValue // pk 지정 , 번호 자동 증가
    private Long id;

    /**
     * order : member = 다 : 1 관계 , fetch=Lazy 지연관계 이용
     * member Entity는 Order Entity가 실제 사용 될 때 까지 로딩을 지연시킨다.
     * FK 정의 , 1:다 관계에서 '다'가 FK를 관리한다. member를 membr_id라는 이름으로 FK 형태로 관리
     * 이렇게 하면 member를 조회할 때 member가 가지고 있는 다수의 order를 조회 할 수 있다
     * order를 조회 할 때는 어떤 member에게 속해있는지 알 수 있다.
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * order : delivery = 1:1 관계 , 지연로딩 하며  cascade 관계 - order를 삭제하거나 생성할 때 delivery도 같이 삭제되거나 생성
     * JoinColumn은 FK를 정의 , 1:1이지만 order가 주로써 delivery의 FK를 관리한다
     */
    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    /**
     * order : orderItem = 1:다 관계 , order가 생성되거나 삭제되면 orderItem도 같이 cascade 된다.
     */
    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();


    private LocalDateTime orderDate;


    @Enumerated(EnumType.STRING) // orderstatus Enum type 어노테이션
    private OrderStatus orderStatus;

    // 연관관계 편의 메서드
    public void setMember(Member member) { // member Setter ,
        this.member = member;
        member.getOrderList().add(this);
    }

    // public static mehtod로 Order 클래스 생성
    public static Order createOrder(Member member, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.orderDate = LocalDateTime.now();
        order.orderStatus = OrderStatus.ORDERED;
        order.delivery = Delivery.createDelivery(order, member.getAddress().getCity(),
                member.getAddress().getState(),
                member.getAddress().getStreet(),
                member.getAddress().getZipcode());
        for (OrderItem orderItem : orderItems) {
            order.orderItemList.add(orderItem);
            orderItem.setOrder(order);
        }
        return order;
    }
    // 주문취소 비즈니스 로직
    public void cancel() {
        if (delivery.getDeliveryStatus() == Delivery.DeliveryStatus.DONE) {
            throw new IllegalStateException("배송 완료했다 양아치야");
        }
        this.orderStatus = OrderStatus.CANCELED;
        for (OrderItem orderItem : orderItemList) {
            orderItem.cancel();
        }
    }
    //TotalPrice 산출
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItemList) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
