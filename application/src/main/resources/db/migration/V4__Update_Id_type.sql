ALTER TABLE sales_history ADD COLUMN id_new BIGSERIAL;

ALTER TABLE sales_history DROP COLUMN id;

ALTER TABLE sales_history RENAME COLUMN id_new TO id;

ALTER TABLE sales_history ADD PRIMARY KEY (id);