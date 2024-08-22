CREATE TABLE Transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(200) ,
    amount DECIMAL(15, 2) NOT NULL,
    account_id INT NOT NULL,
    type VARCHAR(30) ,
    FOREIGN KEY (account_id) REFERENCES Account(id) ON DELETE CASCADE,
    FOREIGN KEY (transactionType_id) REFERENCES TransactionType(id)
);