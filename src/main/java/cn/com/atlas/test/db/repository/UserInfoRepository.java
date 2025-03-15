package cn.com.atlas.test.db.repository;

import cn.com.atlas.test.db.po.UserInfo;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    @Nonnull
    Optional<UserInfo> findById(@Nonnull Long id);
}
