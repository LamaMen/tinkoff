ALTER TABLE money_transaction
    ADD COLUMN currency VARCHAR(3) DEFAULT 'EUR' NOT NULL;