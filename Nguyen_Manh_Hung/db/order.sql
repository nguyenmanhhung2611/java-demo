-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 21, 2015 at 12:18 PM
-- Server version: 5.6.21
-- PHP Version: 5.5.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_coupon`
--

CREATE TABLE IF NOT EXISTS `tbl_coupon` (
`id` int(11) NOT NULL,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `code` varchar(13) COLLATE utf8_bin NOT NULL,
  `start_date` datetime NOT NULL,
  `expired_date` datetime NOT NULL,
  `coupon_type_id` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `tbl_coupon`
--

INSERT INTO `tbl_coupon` (`id`, `name`, `code`, `start_date`, `expired_date`, `coupon_type_id`, `status`) VALUES
(1, 'Coupon 5', 'IP00000000001', '2015-05-05 00:00:00', '2015-05-30 00:00:00', 1, 0),
(2, 'Coupon 1', 'CP00000000002', '2015-04-30 00:00:00', '2015-05-31 00:00:00', 2, 1),
(3, 'Coupon 4', 'CP00000000003', '2015-04-30 00:00:00', '2015-05-29 00:00:00', 3, 0),
(4, 'Coupon 1', 'CP00000000004', '2015-04-30 00:00:00', '2015-05-31 00:00:00', 2, 1),
(5, 'Coupon 2', 'CP00000000005', '2015-04-30 00:00:00', '2015-05-31 00:00:00', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_coupon_type`
--

CREATE TABLE IF NOT EXISTS `tbl_coupon_type` (
`id` int(11) NOT NULL,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `discount` float NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `tbl_coupon_type`
--

INSERT INTO `tbl_coupon_type` (`id`, `name`, `discount`) VALUES
(1, 'Ruby', 10),
(2, 'Diamond', 18.5),
(3, 'Emerald', 25.5),
(4, 'Sapphire', 28);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_order`
--

CREATE TABLE IF NOT EXISTS `tbl_order` (
`id` int(11) NOT NULL,
  `coupon_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `discount` float NOT NULL,
  `address_info` text COLLATE utf8_bin NOT NULL,
  `total_payment` double(12,0) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `tbl_order`
--

INSERT INTO `tbl_order` (`id`, `coupon_id`, `user_id`, `discount`, `address_info`, `total_payment`) VALUES
(1, 1, 1, 10, '789 ung van khiem', 35550000);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_orderdetail`
--

CREATE TABLE IF NOT EXISTS `tbl_orderdetail` (
`id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `creation_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `tbl_orderdetail`
--

INSERT INTO `tbl_orderdetail` (`id`, `quantity`, `order_id`, `product_id`, `creation_date`) VALUES
(2, 1, 1, 1, '2015-04-21 11:02:51'),
(3, 1, 1, 2, '2015-04-21 11:02:51');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_product`
--

CREATE TABLE IF NOT EXISTS `tbl_product` (
`id` int(11) NOT NULL,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  `code` varchar(13) COLLATE utf8_bin NOT NULL,
  `stock_quantity` int(11) NOT NULL,
  `sale_price` double(10,0) NOT NULL,
  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `tbl_product`
--

INSERT INTO `tbl_product` (`id`, `name`, `code`, `stock_quantity`, `sale_price`, `last_update`) VALUES
(1, 'Iphone 6 Plus 16GB', 'IP00000000016', 40, 16500000, '2015-04-21 09:44:02'),
(2, 'Iphone 6 Plus 64GB', 'IP00000000064', 100, 23000000, '2015-04-21 09:45:15'),
(3, 'Iphone 6 Plus 128GB', 'IP00000000128', 30, 26000000, '2015-04-21 09:45:15'),
(4, 'Iphone 6 Plus Gold 16GB', 'IPG0000000016', 34, 20000000, '2015-04-21 09:46:42'),
(5, 'Iphone 6 Plus Gold 64GB', 'IPG0000000064', 65, 24000000, '2015-04-21 09:46:42'),
(6, 'Iphone 6 Plus Gold 128GB', 'IPG0000000128', 65, 27000000, '2015-04-21 09:47:24');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user`
--

CREATE TABLE IF NOT EXISTS `tbl_user` (
`id` int(11) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `isactive` tinyint(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_user`
--

INSERT INTO `tbl_user` (`id`, `user_name`, `password`, `first_name`, `last_name`, `isactive`) VALUES
(1, 'hung', 'c4410f72e4467dfe7d9cd78edbb2f5786bdccaa54a6010782b2c411f988e717a', 'hung', 'tran', 0),
(2, 'hieu', 'afc8e16842061ea3dbb023bf5f08d1bc3a728429313fab0cba30f60954ff9064', 'hieu', 'tran', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_coupon`
--
ALTER TABLE `tbl_coupon`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_coupon_type`
--
ALTER TABLE `tbl_coupon_type`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_order`
--
ALTER TABLE `tbl_order`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_orderdetail`
--
ALTER TABLE `tbl_orderdetail`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_product`
--
ALTER TABLE `tbl_product`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_user`
--
ALTER TABLE `tbl_user`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_coupon`
--
