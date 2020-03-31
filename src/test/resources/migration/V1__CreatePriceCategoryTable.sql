CREATE TABLE PRICE_INFO( pricing_category VARCHAR (20) PRIMARY KEY,
                            initial_pricing_period INT,
                            initial_price DECIMAL(4,2),
                            price_per_week DECIMAL(4,2),
                            price_per_day DECIMAL(4,2)
                            );