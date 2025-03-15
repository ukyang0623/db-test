package cn.com.atlas.test.db.po;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "product_info")
public class ProductInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('product_info_id_seq')")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "attributes")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> attributes;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

}