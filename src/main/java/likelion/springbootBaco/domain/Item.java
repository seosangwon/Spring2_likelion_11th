package likelion.springbootBaco.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity // 컴포넌트스캔
@Getter // 롬복
@Setter // 롬복
@NoArgsConstructor // 롬복 : 파라미터 없이 생성자 생성
public class Item {
    @Id @GeneratedValue // pk 지정 + pk 번호 자동 증가
    private Long id;

    @OneToMany(mappedBy = "item") // item : orderItem = 1:다 관계 . orderItem의 item필드에 의해 관리 받음
    private List<OrderItem> orderItem = new ArrayList<>();

    private String brand;
    private String name;
    private Integer price;
    private Integer stock;

    /**
     * 비즈니스 로직
     */
    @Comment("재고 추가") // quantity를 파라미터로 받으면 받은 량 많은 재고를 추가
    public void addStock(int quantity) {
        this.stock += quantity;
    }

    @Comment("재고 감소") // orderItem에서 받은 파라미터 stockQuantity만큼 재고 감소, 재고 수량 초과시 Exception
    public void removeStock(int stockQuantity) {
        int restStock = this.stock - stockQuantity;
        if (restStock < 0) {
            throw new IllegalStateException("need more stock");
        }
        this.stock = restStock;
    }
}
