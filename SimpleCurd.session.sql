

-- Insert sample users
INSERT INTO users (name, email, phone, address, is_active) VALUES
('John Doe', 'john.doe@example.com', '555-0101', '123 Main St, New York, NY 10001', TRUE),
('Jane Smith', 'jane.smith@example.com', '555-0102', '456 Oak Ave, Los Angeles, CA 90001', TRUE),
('Michael Johnson', 'michael.j@example.com', '555-0103', '789 Pine Rd, Chicago, IL 60601', TRUE),
('Emily Davis', 'emily.davis@example.com', '555-0104', '321 Elm St, Houston, TX 77001', FALSE),
('Robert Brown', 'robert.brown@example.com', '555-0105', '654 Maple Dr, Phoenix, AZ 85001', TRUE);

-- Insert sample products
INSERT INTO products (name, description, price, quantity, category, is_available, created_at, updated_at) VALUES
('Laptop Dell XPS 15', 'High-performance laptop with Intel i7 processor, 16GB RAM, 512GB SSD', 1299.99, 25, 'Electronics', TRUE, NOW(), NOW()),
('iPhone 14 Pro', 'Latest Apple smartphone with A16 Bionic chip, 256GB storage', 999.99, 50, 'Electronics', TRUE, NOW(), NOW()),
('Sony WH-1000XM5', 'Premium noise-canceling wireless headphones', 399.99, 75, 'Electronics', TRUE, NOW(), NOW()),
('Samsung 55" 4K TV', 'Smart LED TV with HDR support and streaming apps', 649.99, 15, 'Electronics', TRUE, NOW(), NOW()),
('Office Chair Ergonomic', 'Comfortable mesh office chair with lumbar support', 249.99, 40, 'Furniture', TRUE, NOW(), NOW()),
('Standing Desk', 'Adjustable height electric standing desk, 60x30 inches', 499.99, 10, 'Furniture', TRUE, NOW(), NOW()),
('Wireless Mouse', 'Bluetooth ergonomic mouse with USB-C charging', 39.99, 150, 'Accessories', TRUE, NOW(), NOW()),
('Mechanical Keyboard', 'RGB backlit gaming keyboard with Cherry MX switches', 129.99, 0, 'Accessories', FALSE, NOW(), NOW()),
('USB-C Hub', '7-in-1 multiport adapter with HDMI, USB 3.0, SD card reader', 49.99, 200, 'Accessories', TRUE, NOW(), NOW()),
('Portable SSD 1TB', 'External solid state drive with USB 3.2 Gen 2', 159.99, 60, 'Storage', TRUE, NOW(), NOW());

-- Query to display all users
SELECT * FROM users;

-- Query to display all products
SELECT * FROM products;

-- Query to display only available products
SELECT * FROM products WHERE is_available = TRUE;

