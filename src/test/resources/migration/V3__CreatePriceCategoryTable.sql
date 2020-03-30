CREATE TABLE PRICE_CATEGORY(id SERIAL PRIMARY KEY,
                            name VARCHAR (20) NOT NULL,
                            initial_pricing_period DECIMAL(3,0),
                            initial_price DECIMAL(4,2),
                            price_per_week DECIMAL(4,2),
                            price_per_day DECIMAL(4,2)
                            );