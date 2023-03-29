package labshopmonolith.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import labshopmonolith.MonotolithApplication;
import labshopmonolith.domain.OrderPlaced;
import lombok.Data;

@Entity
@Table(name = "Order_table")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productId;

    private Integer qty;

    private String customerId;

    private Double amount;

    @PostPersist
    public void onPostPersist() {
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        labshopmonolith.external.Inventory inventory = new labshopmonolith.external.Inventory();
        // mappings goes here
        MonotolithApplication.applicationContext
            .getBean(labshopmonolith.external.InventoryService.class)
            .decreaseStock(inventory);

        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();
    }

    @PrePersist
    public void onPrePersist() {}

    public static OrderRepository repository() {
        OrderRepository orderRepository = MonotolithApplication.applicationContext.getBean(
            OrderRepository.class
        );
        return orderRepository;
    }
}
