CREATE TABLE PRICE_CATEGORY(id SERIAL PRIMARY KEY,
                            name VARCHAR (20) NOT NULL,
                            initial_pricing_period NUMERIC(3,0),
                            initial_price NUMERIC(4,2),
                            price_per_week NUMERIC(4,2),
                            price_per_day NUMERIC(4,2)
                            );