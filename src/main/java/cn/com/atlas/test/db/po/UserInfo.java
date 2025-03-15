package cn.com.atlas.test.db.po;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('user_info_id_seq')")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_time")
    private OffsetDateTime createdTime;

    @Column(name = "last_login_time")
    private OffsetDateTime lastLoginTime;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean active;

    @OneToMany
    private Set<OrderInfo> orderInfos = new LinkedHashSet<>();

}