CREATE TABLE PRICE_INFO(pricing_category VARCHAR (20) PRIMARY KEY,
                            initial_pricing_period INTEGER,
                            initial_price NUMERIC(4,2),
                            price_per_week NUMERIC(4,2),
                            price_per_day NUMERIC(4,2)
                            );