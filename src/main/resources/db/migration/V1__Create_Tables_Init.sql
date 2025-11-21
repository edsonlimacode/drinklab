CREATE TABLE groups (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL
);

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nick_name VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE,
    active BOOLEAN DEFAULT true,
    group_id BIGINT NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups(id)
);

CREATE TABLE payment_methods (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE user_payment_methods (
    user_id BIGINT NOT NULL,
    payment_method_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id),
    PRIMARY KEY (user_id)
);

--Armazem de estoque
CREATE TABLE warehouse (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    user_id BIGINT NOT NULL,
    address TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Clientes
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    document VARCHAR(20),
    contact VARCHAR(50),
    active BOOLEAN DEFAULT true,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

--Fornecedor
CREATE TABLE suppliers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    document VARCHAR(18) UNIQUE,
    phone VARCHAR(20),
    email VARCHAR(120),
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    sku VARCHAR(50) UNIQUE,
    name VARCHAR(255) NOT NULL,
    brand VARCHAR(100),
    unit VARCHAR(20) NOT NULL,
    price NUMERIC(12,2) NOT NULL,
    active BOOLEAN DEFAULT true,
    category_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Compra do fornecedor
CREATE TABLE purchase_order (
    id BIGSERIAL PRIMARY KEY,
    supplier_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    total DECIMAL(12,2) NOT NULL,
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(30) NOT NULL DEFAULT 'OPEN',
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- item da compra do fornecedor
CREATE TABLE purchase_item (
    id BIGSERIAL PRIMARY KEY,
    purchase_order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    cost_price DECIMAL(12,2) NOT NULL,

    FOREIGN KEY (purchase_order_id) REFERENCES purchase_order(id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Pedidos de venda
CREATE TABLE sale_order (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(30) NOT NULL,
    total NUMERIC(10,2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE sale_item (
    id BIGSERIAL PRIMARY KEY,
    sale_order_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    qty INTEGER NOT NULL,
    unit_price NUMERIC(14,2) NOT NULL,
    foreign KEY (sale_order_id) REFERENCES sale_order(id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE route_delivery (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    sale_order_id BIGINT NOT NULL,
    driver_name VARCHAR(50) NOT NULL,
    status BOOLEAN DEFAULT false,
    FOREIGN KEY (sale_order_id)  REFERENCES sale_order(id)
);

CREATE TABLE stock (
    id BIGSERIAL PRIMARY KEY,
    quantity INTEGER DEFAULT 0,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouse(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE(product_id, warehouse_id)
);

-- Movimentações de estoque
CREATE TABLE stock_movement (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    qty NUMERIC(14,3) NOT NULL,
    type VARCHAR(10) NOT NULL,
    reference TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id)  REFERENCES products(id),
    FOREIGN KEY (user_id)  REFERENCES users(id)
);