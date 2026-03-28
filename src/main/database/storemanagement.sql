-- =========================
-- CREATE DATABASE
-- =========================
CREATE DATABASE IF NOT EXISTS StoreManagement;
USE StoreManagement;

-- =========================
-- SUPPLIERS
-- =========================
CREATE TABLE SUPPLIERS (
                           SupplierID INT AUTO_INCREMENT PRIMARY KEY,
                           SupplierName VARCHAR(100) NOT NULL,
                           Address VARCHAR(255),
                           Phone VARCHAR(20)
);

-- =========================
-- CATEGORIES
-- =========================
CREATE TABLE CATEGORIES (
                            CategoryID INT AUTO_INCREMENT PRIMARY KEY,
                            CategoryName VARCHAR(100) NOT NULL
);

-- =========================
-- BRANCHES
-- =========================
CREATE TABLE BRANCHES (
                          BranchID INT AUTO_INCREMENT PRIMARY KEY,
                          BranchName VARCHAR(100) NOT NULL,
                          Address VARCHAR(255),
                          Phone VARCHAR(20)
);

-- =========================
-- PRODUCTS
-- =========================
CREATE TABLE PRODUCTS (
                          ProductID INT AUTO_INCREMENT PRIMARY KEY,
                          ProductName VARCHAR(100) NOT NULL,
                          UnitPrice DECIMAL(10,2) NOT NULL,
                          CategoryID INT NULL,
                          Status BOOLEAN,
                          FOREIGN KEY (CategoryID) REFERENCES CATEGORIES(CategoryID)
                              ON DELETE SET NULL
                              ON UPDATE CASCADE
);

-- =========================
-- CUSTOMERS
-- =========================
CREATE TABLE CUSTOMERS (
                           CustomerID INT AUTO_INCREMENT PRIMARY KEY,
                           CustomerName VARCHAR(100) NOT NULL,
                           Phone VARCHAR(20),
                           Address VARCHAR(255)
);

-- =========================
-- EMPLOYEES
-- =========================
CREATE TABLE EMPLOYEES (
                           EmployeeID INT AUTO_INCREMENT PRIMARY KEY,
                           EmployeeName VARCHAR(100) NOT NULL,
                           Position VARCHAR(50),
                           Salary DECIMAL(10,2),
                           BranchID INT NULL,
                           FOREIGN KEY (BranchID) REFERENCES BRANCHES(BranchID)
                               ON DELETE SET NULL
                               ON UPDATE CASCADE
);

-- =========================
-- BATCHES
-- =========================
CREATE TABLE BATCHES (
                         BatchID INT AUTO_INCREMENT PRIMARY KEY,
                         ProductID INT NULL,
                         SupplierID INT NULL,
                         BranchID INT NULL,
                         ImportDate DATE,
                         Quantity INT,
                         FOREIGN KEY (ProductID) REFERENCES PRODUCTS(ProductID)
                             ON DELETE SET NULL
                             ON UPDATE CASCADE,
                         FOREIGN KEY (SupplierID) REFERENCES SUPPLIERS(SupplierID)
                             ON DELETE SET NULL
                             ON UPDATE CASCADE,
                         FOREIGN KEY (BranchID) REFERENCES BRANCHES(BranchID)
                             ON DELETE SET NULL
                             ON UPDATE CASCADE
);

-- =========================
-- INVOICES
-- =========================
CREATE TABLE INVOICES (
                          InvoiceID INT AUTO_INCREMENT PRIMARY KEY,
                          CustomerID INT NULL,
                          EmployeeID INT NULL,
                          BranchID INT NULL,
                          InvoiceDate DATE,
                          TotalAmount DECIMAL(12,2),
                          FOREIGN KEY (CustomerID) REFERENCES CUSTOMERS(CustomerID)
                              ON DELETE SET NULL
                              ON UPDATE CASCADE,
                          FOREIGN KEY (EmployeeID) REFERENCES EMPLOYEES(EmployeeID)
                              ON DELETE SET NULL
                              ON UPDATE CASCADE,
                          FOREIGN KEY (BranchID) REFERENCES BRANCHES(BranchID)
                              ON DELETE SET NULL
                              ON UPDATE CASCADE
);

-- =========================
-- INVOICE_DETAILS (FIX CHUẨN)
-- =========================
CREATE TABLE INVOICE_DETAILS (
                                 InvoiceID INT,
                                 ProductID INT,
                                 Quantity INT NOT NULL,
                                 UnitPrice DECIMAL(10,2) NOT NULL,
                                 PRIMARY KEY (InvoiceID, ProductID),
                                 FOREIGN KEY (InvoiceID) REFERENCES INVOICES(InvoiceID)
                                     ON DELETE CASCADE
                                     ON UPDATE CASCADE,
                                 FOREIGN KEY (ProductID) REFERENCES PRODUCTS(ProductID)
                                     ON DELETE CASCADE
                                     ON UPDATE CASCADE
);

-- =========================
-- INSERT DATA
-- =========================

INSERT INTO SUPPLIERS VALUES
                          (1,'Nhà cung cấp A','Hà Nội','0900000001'),
                          (2,'Nhà cung cấp B','TP.HCM','0900000002'),
                          (3,'Nhà cung cấp C','Đà Nẵng','0900000003');

INSERT INTO CATEGORIES VALUES
                           (1,'Điện thoại'),
                           (2,'Laptop'),
                           (3,'Phụ kiện');

INSERT INTO BRANCHES VALUES
                         (1,'Chi nhánh Q1','TP.HCM','0911111111'),
                         (2,'Chi nhánh Q7','TP.HCM','0922222222'),
                         (3,'Chi nhánh Hà Nội','Hà Nội','0933333333');

INSERT INTO PRODUCTS VALUES
                         (1,'iPhone 14',20000000,1,1),
                         (2,'Samsung S23',18000000,1,1),
                         (3,'MacBook M2',30000000,2,1),
                         (4,'Dell XPS',28000000,2,1),
                         (5,'Tai nghe',500000,3,1);

INSERT INTO CUSTOMERS VALUES
                          (1,'Nguyễn Văn A','0988888881','TP.HCM'),
                          (2,'Trần Thị B','0988888882','Hà Nội'),
                          (3,'Lê Văn C','0988888883','Đà Nẵng');

INSERT INTO EMPLOYEES VALUES
                          (1,'NV1','Bán hàng',10000000,1),
                          (2,'NV2','Thu ngân',9000000,1),
                          (3,'NV3','Quản lý',15000000,2),
                          (4,'NV4','Bán hàng',9500000,3);

INSERT INTO BATCHES VALUES
                        (1,1,1,1,'2025-01-01',50),
                        (2,2,2,1,'2025-01-05',40),
                        (3,3,1,2,'2025-01-10',30),
                        (4,4,3,2,'2025-01-15',20),
                        (5,5,2,3,'2025-01-20',100);

INSERT INTO INVOICES VALUES
                         (1,1,1,1,'2025-02-01',20500000),
                         (2,2,2,1,'2025-02-02',18000000),
                         (3,3,3,2,'2025-02-03',30000000);

INSERT INTO INVOICE_DETAILS VALUES
                                (1,1,1,20000000),
                                (1,5,1,500000),
                                (2,2,1,18000000),
                                (3,3,1,30000000);