-- Создание таблицы партнеров
CREATE TABLE partners (
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(255) NOT NULL,
    partner_type varchar(20),
    director varchar(120),
    email varchar(120),
    phone_number varchar,
    address varchar,
    inn bigint,
    rating int
);

-- Создание таблицы типов продукции
CREATE TABLE product_types (
    id BIGSERIAL PRIMARY KEY ,
    type_name VARCHAR(255) NOT NULL UNIQUE,
    coefficient DECIMAL(10,2) NOT NULL
);

-- Создание таблицы материалов
CREATE TABLE materials (
    id BIGSERIAL PRIMARY KEY ,
    material_name VARCHAR(255) NOT NULL UNIQUE,
    defect_percent DECIMAL(5,2) NOT NULL
);

-- Создание таблицы продукции
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY ,
    product_name VARCHAR(255) NOT NULL,
    product_type_id BIGINT NOT NULL,
    FOREIGN KEY (product_type_id)
      REFERENCES product_types(id)
);

-- Создание таблицы истории продаж
CREATE TABLE sales_history (
    id BIGINT PRIMARY KEY ,
    partner_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    sale_date DATE NOT NULL,
    FOREIGN KEY (partner_id)
       REFERENCES partners(id),
    FOREIGN KEY (product_id)
       REFERENCES products(id)
);

-- Дополнительные ограничения целостности
ALTER TABLE materials
    ADD CHECK (defect_percent >= 0 AND defect_percent <= 100);

ALTER TABLE sales_history
    ADD CHECK (quantity > 0);