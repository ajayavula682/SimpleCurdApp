-- Insert sample users
INSERT IGNORE INTO users (id, name, email, phone, address, is_active) VALUES
(1, 'John Doe', 'john.doe@example.com', '555-0101', '123 Main St, New York, NY 10001', TRUE),
(2, 'Jane Smith', 'jane.smith@example.com', '555-0102', '456 Oak Ave, Los Angeles, CA 90001', TRUE),
(3, 'Michael Johnson', 'michael.j@example.com', '555-0103', '789 Pine Rd, Chicago, IL 60601', TRUE),
(4, 'Emily Davis', 'emily.davis@example.com', '555-0104', '321 Elm St, Houston, TX 77001', FALSE),
(5, 'Robert Brown', 'robert.brown@example.com', '555-0105', '654 Maple Dr, Phoenix, AZ 85001', TRUE);

-- Insert sample products
INSERT IGNORE INTO products (id, name, description, price, quantity, category, is_available, created_at, updated_at) VALUES
(1, 'Laptop Dell XPS 15', 'High-performance laptop with Intel i7 processor, 16GB RAM, 512GB SSD', 1299.99, 25, 'Electronics', TRUE, NOW(), NOW()),
(2, 'iPhone 14 Pro', 'Latest Apple smartphone with A16 Bionic chip, 256GB storage', 999.99, 50, 'Electronics', TRUE, NOW(), NOW()),
(3, 'Sony WH-1000XM5', 'Premium noise-canceling wireless headphones', 399.99, 75, 'Electronics', TRUE, NOW(), NOW()),
(4, 'Samsung 55" 4K TV', 'Smart LED TV with HDR support and streaming apps', 649.99, 15, 'Electronics', TRUE, NOW(), NOW()),
(5, 'Office Chair Ergonomic', 'Comfortable mesh office chair with lumbar support', 249.99, 40, 'Furniture', TRUE, NOW(), NOW()),
(6, 'Standing Desk', 'Adjustable height electric standing desk, 60x30 inches', 499.99, 10, 'Furniture', TRUE, NOW(), NOW()),
(7, 'Wireless Mouse', 'Bluetooth ergonomic mouse with USB-C charging', 39.99, 150, 'Accessories', TRUE, NOW(), NOW()),
(8, 'Mechanical Keyboard', 'RGB backlit gaming keyboard with Cherry MX switches', 129.99, 0, 'Accessories', FALSE, NOW(), NOW()),
(9, 'USB-C Hub', '7-in-1 multiport adapter with HDMI, USB 3.0, SD card reader', 49.99, 200, 'Accessories', TRUE, NOW(), NOW()),
(10, 'Portable SSD 1TB', 'External solid state drive with USB 3.2 Gen 2', 159.99, 60, 'Storage', TRUE, NOW(), NOW());