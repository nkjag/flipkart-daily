INSERT INTO items (brand, category, price, quantity) VALUES
('Amul', 'Milk', 52, 120),
('Parle', 'Biscuit', 10, 400),
('Nestle', 'Coffee', 210, 50)
ON CONFLICT DO NOTHING;
