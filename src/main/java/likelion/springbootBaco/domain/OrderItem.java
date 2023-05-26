package likelion.springbootBaco.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity// 컴포넌트 스캔
@NoArgsConstructor(access = PROTECTED) // 롬복 생성자
@Getter // 롬복
public class OrderItem {
    @Id
    @GeneratedValue // pk 지정 번호 자동 증가
    private Long id;

    /**
     * OrderItem : Order = 다 : 1 관계 -> 1개의 Order는 여러개의 OrderItem을 가질 수 있다.
     * 지연로딩 : orderItem을 가져오는데 order는 조금 있다가 로딩 되는걸루
     * 다인 OrderItem이 Order의 FK를 관리한다.
     */

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * OrderItem : Item = 다 : 1 관계
     * '다'인 OrderItem이 Item의 FK를 item_id라는 이름으로 관리
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer price;
    private Integer count;

    /**
     * 스태틱 팩토리 메서드
     *
     */
    // 해당 파라미터를 인자로 받아 OrderItem 인스턴스 생성
    public static OrderItem createOrderItem(Item item, int orderPrice, int orderCount) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.price = orderPrice;
        orderItem.count = orderCount;
        // 연관관계 편의 메서드 : // 생성되는 수 만큼 재고 줄여주기
        item.removeStock(orderCount);
        return orderItem;
    }


    public void setOrder(Order order) {
        this.order = order;
        order.getOrderItemList().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
        item.getOrderItem().add(this);
    }

    /**
     * 비즈니스 로직
     */
    // item 수량 * 가격
    public int getTotalPrice() {
        return this.getPrice() * this.getCount();
    }

    public void cancel() {
        this.getItem().addStock(count);
    }
}
