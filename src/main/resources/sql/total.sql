-- 通过编程方式检查数据库是否存在
DO
$$
BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'atlas') THEN
            CREATE DATABASE atlas
                WITH
                OWNER = ukyang
                ENCODING = 'UTF8'
                CONNECTION LIMIT = -1;
END IF;
END
$$;

-- 切换到新数据库（在 psql 命令行中执行）
\c atlas

-- 用户表（含数据校验）
CREATE TABLE IF NOT EXISTS user_info
(
    id              SERIAL PRIMARY KEY,                                      -- 自增主键
    username        VARCHAR(50)  NOT NULL UNIQUE,
    email           VARCHAR(255) NOT NULL
    CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'), -- 邮箱正则校验
    password        VARCHAR(255) NOT NULL,
    created_time    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    last_login_time TIMESTAMPTZ,
    is_active       BOOLEAN     DEFAULT TRUE
    );

-- 商品表（含JSONB字段）
CREATE TABLE IF NOT EXISTS product_info
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(200)   NOT NULL,
    price          NUMERIC(10, 2) NOT NULL CHECK (price > 0),
    stock_quantity INT            NOT NULL CHECK (stock_quantity >= 0),
    attributes     JSONB, -- 存储商品特性（颜色、尺寸等）
    category       VARCHAR(50)    NOT NULL
    );

-- 订单表（含外键约束）
CREATE TABLE IF NOT EXISTS order_info
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      INT            NOT NULL REFERENCES user_info (id) ON DELETE CASCADE,
    order_date   DATE           NOT NULL DEFAULT CURRENT_DATE,
    total_amount NUMERIC(12, 2) NOT NULL CHECK (total_amount >= 0),
    status       VARCHAR(20)
    CONSTRAINT orders_status_check -- 在此处命名约束
    CHECK (status IN ('pending', 'paid', 'shipped', 'cancelled'))
    );

-- 订单明细表（复合主键）
CREATE TABLE IF NOT EXISTS order_items
(
    order_id   BIGINT         NOT NULL REFERENCES order_info (id) ON DELETE CASCADE,
    product_id INT            NOT NULL REFERENCES product_info (id),
    quantity   INT            NOT NULL CHECK (quantity > 0),
    unit_price NUMERIC(10, 2) NOT NULL,
    PRIMARY KEY (order_id, product_id) -- 复合主键
    );

-- 用户邮箱快速查询
CREATE INDEX IF NOT EXISTS idx_users_email ON user_info (email);

-- 商品分类统计优化
CREATE INDEX IF NOT EXISTS idx_products_category ON product_info (category);

-- 订单状态过滤优化
CREATE INDEX IF NOT EXISTS idx_orders_status ON order_info (status);

COMMENT ON TABLE user_info IS '系统注册用户表';
COMMENT ON COLUMN product_info.attributes IS '商品扩展属性，使用JSONB格式存储';
COMMENT ON CONSTRAINT orders_status_check ON order_info IS '订单状态枚举校验';

-- 插入用户
INSERT INTO user_info (username, email, password)
VALUES ('john_doe', 'john@example.com', 'hashed_password_123'),
       ('jane_smith', 'jane@example.com', 'hashed_password_456');

-- 插入商品
INSERT INTO product_info (name, price, stock_quantity, category, attributes)
VALUES ('Laptop', 999.99, 10, 'Electronics', '{
  "brand": "Dell",
  "ram": "16GB"
}'),
       ('T-Shirt', 29.99, 50, 'Clothing', '{
         "size": "L",
         "color": "Blue"
       }');