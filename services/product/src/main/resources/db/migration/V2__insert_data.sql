-- Insert data into category table
INSERT INTO category (id, description, name)
VALUES
    (nextval('category_seq'), 'Electronics and gadgets', 'Electronics'),
    (nextval('category_seq'), 'Home and kitchen appliances', 'Home Appliances'),
    (nextval('category_seq'), 'Books and magazines', 'Books'),
    (nextval('category_seq'), 'Clothing and accessories', 'Fashion'),
    (nextval('category_seq'), 'Sports and outdoor equipment', 'Sports'),
    (nextval('category_seq'), 'Health and beauty products', 'Health & Beauty');

-- Verify the inserted category data
SELECT * FROM category;

-- Insert data into product table
INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES
    (nextval('product_seq'), 'Smartphone with 128GB storage', 'Smartphone', 50, 699.99, (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'Blender with 5 speed settings', 'Blender', 30, 49.99, (SELECT id FROM category WHERE name = 'Home Appliances')),
    (nextval('product_seq'), 'Science fiction novel', 'Novel', 100, 19.99, (SELECT id FROM category WHERE name = 'Books')),
    (nextval('product_seq'), '4K Ultra HD Smart TV', 'Smart TV', 20, 1299.99, (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'Wireless earbuds with noise cancellation', 'Earbuds', 75, 199.99, (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'Refrigerator with ice maker', 'Refrigerator', 10, 899.99, (SELECT id FROM category WHERE name = 'Home Appliances')),
    (nextval('product_seq'), 'Stand mixer with 10 speeds', 'Stand Mixer', 15, 299.99, (SELECT id FROM category WHERE name = 'Home Appliances')),
    (nextval('product_seq'), 'Mystery thriller novel', 'Thriller Novel', 150, 24.99, (SELECT id FROM category WHERE name = 'Books')),
    (nextval('product_seq'), 'Women''s summer dress', 'Summer Dress', 40, 49.99, (SELECT id FROM category WHERE name = 'Fashion')),
    (nextval('product_seq'), 'Men''s running shoes', 'Running Shoes', 25, 79.99, (SELECT id FROM category WHERE name = 'Fashion')),
    (nextval('product_seq'), 'Mountain bike', 'Bike', 8, 599.99, (SELECT id FROM category WHERE name = 'Sports')),
    (nextval('product_seq'), 'Yoga mat', 'Yoga Mat', 100, 19.99, (SELECT id FROM category WHERE name = 'Sports')),
    (nextval('product_seq'), 'Skincare set', 'Skincare', 50, 39.99, (SELECT id FROM category WHERE name = 'Health & Beauty')),
    (nextval('product_seq'), 'Hair dryer with diffuser', 'Hair Dryer', 30, 59.99, (SELECT id FROM category WHERE name = 'Health & Beauty'));