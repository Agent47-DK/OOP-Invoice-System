-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 31, 2023 at 06:47 PM
-- Server version: 8.0.31
-- PHP Version: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `c00290922`
--

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
CREATE TABLE IF NOT EXISTS `bill` (
  `billId` int NOT NULL,
  `dateCreated` date NOT NULL,
  `amountDue` decimal(10,2) NOT NULL,
  `status` enum('unpaid','paid','partial') NOT NULL,
  `dateDue` date NOT NULL,
  `totalCost` decimal(10,2) NOT NULL,
  `amountPaid` decimal(10,2) NOT NULL,
  `vendorId` int NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`billId`),
  KEY `vendor-bill` (`vendorId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bill_payment`
--

DROP TABLE IF EXISTS `bill_payment`;
CREATE TABLE IF NOT EXISTS `bill_payment` (
  `billPaymentId` int NOT NULL,
  `method` enum('card','cash','eft') NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `paymentDate` date NOT NULL,
  `notes` varchar(50) NOT NULL,
  `billId` int NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`billPaymentId`),
  KEY `bill-bill_payment` (`billId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bill_product`
--

DROP TABLE IF EXISTS `bill_product`;
CREATE TABLE IF NOT EXISTS `bill_product` (
  `billProductId` int NOT NULL,
  `billId` int NOT NULL,
  `productId` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`billProductId`),
  KEY `bill-bill_product` (`billId`),
  KEY `product-bill_product` (`productId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `customerId` int NOT NULL,
  `name` varchar(30) NOT NULL COMMENT 'Business name or person',
  `firstname` varchar(30) NOT NULL COMMENT 'primary contact',
  `lastname` varchar(30) NOT NULL COMMENT 'primary contact',
  `email` varchar(30) NOT NULL COMMENT 'primary contact',
  `contactNo` varchar(15) NOT NULL COMMENT 'primary contact',
  `website` varchar(30) NOT NULL COMMENT 'company site',
  `address` varchar(50) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`customerId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customerId`, `name`, `firstname`, `lastname`, `email`, `contactNo`, `website`, `address`, `deleted`) VALUES
(1, 'Douglas Media', 'Douglas', 'Kadzutu', 'dkadzutu@gmail.com', '0872980486', 'www.douglasmedia.co.za', '15, Baron Street, Carlow, Co Carlow, Ireland', 0),
(2, 'Anonymous Citizen', 'Isheanesu', 'Kadzutu', 'ishe@gmail.com', '0871234567', 'www.anonymouscitizen.com', '6473 Nkulumane,\nBulawayo,\nZimbabwe', 0),
(3, 'Test Updated', 'test2', 'test', 'test@test.com', '09123345', 'www.test.com', '51 Test Address\nTest ', 0);

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
CREATE TABLE IF NOT EXISTS `invoice` (
  `invoiceId` int NOT NULL,
  `dateCreated` date NOT NULL,
  `amountDue` decimal(10,2) NOT NULL,
  `status` enum('draft','approved','sent','paid','overdue') NOT NULL,
  `dueDate` date NOT NULL,
  `totalCost` decimal(10,2) NOT NULL,
  `amountPaid` decimal(10,2) NOT NULL,
  `customerId` int NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`invoiceId`),
  KEY `customerId` (`customerId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`invoiceId`, `dateCreated`, `amountDue`, `status`, `dueDate`, `totalCost`, `amountPaid`, `customerId`, `deleted`) VALUES
(1, '2023-03-22', '100.00', 'draft', '2023-03-31', '100.00', '0.00', 1, 0),
(5, '2023-03-30', '321.50', 'draft', '2023-05-15', '321.50', '0.00', 1, 0),
(4, '2023-03-30', '883.55', 'draft', '2023-08-24', '883.55', '0.00', 2, 0),
(3, '2023-03-30', '383.55', 'draft', '2023-08-24', '383.55', '0.00', 1, 0),
(2, '2023-03-30', '709.87', 'draft', '2023-07-12', '709.87', '0.00', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `invoice_payment`
--

DROP TABLE IF EXISTS `invoice_payment`;
CREATE TABLE IF NOT EXISTS `invoice_payment` (
  `invoicePayId` int NOT NULL,
  `method` enum('card','cash','eft') NOT NULL COMMENT 'payment method',
  `amount` decimal(10,2) NOT NULL,
  `datePayed` date NOT NULL,
  `notes` varchar(100) NOT NULL,
  `invoiceId` int NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`invoicePayId`),
  KEY `invoice-invoicePayment` (`invoiceId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `invoice_product`
--

DROP TABLE IF EXISTS `invoice_product`;
CREATE TABLE IF NOT EXISTS `invoice_product` (
  `invoiceProductId` int NOT NULL,
  `invoiceId` int NOT NULL,
  `productId` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`invoiceProductId`),
  KEY `invoice_product-invoice` (`invoiceId`),
  KEY `invoice_product-product` (`productId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `invoice_product`
--

INSERT INTO `invoice_product` (`invoiceProductId`, `invoiceId`, `productId`, `quantity`) VALUES
(1, 3, 3, 5),
(2, 3, 4, 3),
(3, 3, 1, 2),
(4, 4, 3, 5),
(5, 4, 4, 3),
(6, 4, 1, 2),
(7, 4, 1, 5),
(8, 5, 4, 6),
(9, 5, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `productId` int NOT NULL,
  `name` varchar(30) NOT NULL,
  `description` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `category` enum('buy','sell') NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`productId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`productId`, `name`, `description`, `price`, `category`, `deleted`) VALUES
(1, 'website hosting', 'hosting site per year', '100.00', 'sell', 0),
(2, 'domain purchase', 'purchasing domain for client', '10.00', 'buy', 0),
(3, 'Logo Updated', 'Logo design', '24.56', 'sell', 0),
(4, 'Flyer ', 'Design Flyer', '20.25', 'sell', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `userId` int NOT NULL,
  `username` varchar(20) NOT NULL COMMENT 'used for login',
  `password` varchar(15) NOT NULL,
  `email` varchar(30) NOT NULL,
  `contactNo` varchar(15) NOT NULL,
  `role` enum('Admin','User') NOT NULL COMMENT 'can only be Admin or User',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userId`, `username`, `password`, `email`, `contactNo`, `role`) VALUES
(1, 'Douglas', 'admin', 'douglas@admin.com', '0872980486', 'Admin'),
(2, 'Tafadzwa', 'user', 'tafadzwa@user.com', '0780509648', 'User'),
(3, 'Ornella', '1234', 'ornella@gmail.com', '123', 'User');

-- --------------------------------------------------------

--
-- Table structure for table `vendor`
--

DROP TABLE IF EXISTS `vendor`;
CREATE TABLE IF NOT EXISTS `vendor` (
  `vendorId` int NOT NULL,
  `name` varchar(20) NOT NULL COMMENT 'business name',
  `firstname` varchar(20) NOT NULL COMMENT 'contact person',
  `lastname` varchar(20) NOT NULL COMMENT 'contact person',
  `email` varchar(30) NOT NULL COMMENT 'contact person',
  `address` varchar(100) NOT NULL COMMENT 'business address',
  `contactNo` varchar(15) NOT NULL COMMENT 'contact person',
  `website` varchar(30) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`vendorId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `vendor`
--

INSERT INTO `vendor` (`vendorId`, `name`, `firstname`, `lastname`, `email`, `address`, `contactNo`, `website`, `deleted`) VALUES
(1, 'Bluehost', 'Eric ', 'Cartman', 'eric@bluehost.com', '123, Florida\r\nUSA', '01198763547', 'www.bluehost.com', 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
