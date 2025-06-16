CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(100) NOT NULL,
    category VARCHAR(100) NOT NULL,
    price INTEGER NOT NULL CHECK (price >= 0),
    quantity INTEGER NOT NULL CHECK (quantity >= 0),
    CONSTRAINT brand_category_unique UNIQUE (brand, category)
);
