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
-- PRODUCTS
-- =========================
CREATE TABLE PRODUCTS (
  ProductID INT AUTO_INCREMENT PRIMARY KEY,
  ProductName VARCHAR(100) NOT NULL,
  UnitPrice DECIMAL(10,2) NOT NULL,
  CategoryID INT,
  Status BOOLEAN, -- 1: đã phân cho chi nhánh
  CONSTRAINT fk_product_category
      FOREIGN KEY (CategoryID) REFERENCES CATEGORIES(CategoryID)
          ON DELETE SET NULL
          ON UPDATE CASCADE
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
   BranchID INT,
   FOREIGN KEY (BranchID) REFERENCES BRANCHES(BranchID)
       ON DELETE SET NULL
       ON UPDATE CASCADE
);

-- =========================
-- BATCHES
-- =========================
CREATE TABLE BATCHES (
     BatchID INT AUTO_INCREMENT PRIMARY KEY,
     ProductID INT NOT NULL,
     SupplierID INT NOT NULL,
     ImportDate DATE,
     Quantity INT,
     FOREIGN KEY (ProductID) REFERENCES PRODUCTS(ProductID)
         ON DELETE CASCADE
         ON UPDATE CASCADE,
     FOREIGN KEY (SupplierID) REFERENCES SUPPLIERS(SupplierID)
         ON DELETE CASCADE
         ON UPDATE CASCADE
);

-- =========================
-- BRANCH_PRODUCTS (N-N)
-- =========================
CREATE TABLE BRANCH_PRODUCTS (
     BranchID INT,
     ProductID INT,
     Quantity INT,
     PRIMARY KEY (BranchID, ProductID),
     FOREIGN KEY (BranchID) REFERENCES BRANCHES(BranchID)
         ON DELETE CASCADE
         ON UPDATE CASCADE,
     FOREIGN KEY (ProductID) REFERENCES PRODUCTS(ProductID)
         ON DELETE CASCADE
         ON UPDATE CASCADE
);

-- =========================
-- INVOICES
-- =========================
CREATE TABLE INVOICES (
    InvoiceID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT,
    EmployeeID INT,
    BranchID INT,
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
-- INVOICE_DETAILS (N-N)
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