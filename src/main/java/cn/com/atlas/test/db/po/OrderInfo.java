package cn.com.atlas.test.db.po;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "order_info")
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('order_info_id_seq')")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "status", length = 20)
    private String status;

    @OneToMany(mappedBy = "order")
    private Set<cn.com.atlas.test.db.po.OrderItem> orderItems = new LinkedHashSet<>();

}