-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 05, 2022 at 04:07 PM
-- Server version: 8.0.28
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `zyzz_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `username` varchar(20) NOT NULL,
  `full_name` varchar(50) NOT NULL,
  `password` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(50) NOT NULL,
  `dob` varchar(20) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `register_date` date NOT NULL,
  `client_id` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`username`, `full_name`, `password`, `email`, `dob`, `gender`, `register_date`, `client_id`) VALUES
('alichehade', 'Ali Chehade', '$2y$10$WTErLf/RkuR9rIgsR2aU8ewywFAok.mTVsoCAdc8aftBjHU5GALLW', 'jamil.awada@lau.edu', '21 - 4 - 2022', 'Male', '2022-04-21', 'X4PHWB'),
('charbel2', 'Charbel Daoud', '$2y$10$eOa0XiZ2PmTwCbLrp5NwNe.P01NrNr8JphzH9VNzvfEHbqWb/S1aS', 'mohamad.dhaini02@lau.edu', '3 - 7 - 1997', 'Male', '2022-05-03', '1J7YNX'),
('JamilAwada123', 'Jamil Awada', '$2y$10$0QkHLRX69SmaHPCRlRJTUetzpQHNQ6arbNGxxU8OmylxlLlneJn9m', 'jamilawada@gmail.com', '24 - 5 - 2002', 'Male', '2022-05-04', '8SQ6XW'),
('mdhaini2', 'Abdallah Dhaini', '$2y$10$gGqJgLPE6nqnaEwsaAK36.WaSQfMvs/oLRgIOL4c3qii8sNVzWcu6', 'mohamad.dhaini@lau.edu', '17-9-2002', 'Male', '2022-04-07', 'IT6J7R');

-- --------------------------------------------------------

--
-- Table structure for table `client_info`
--

CREATE TABLE `client_info` (
  `client_id` varchar(6) NOT NULL,
  `client_username` varchar(20) NOT NULL,
  `age` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `height` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `weight` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `past_injuries` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `health_issues` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `days_per_week` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `hours_per_day` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `training_preference` varchar(50) NOT NULL,
  `objectives` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `client_info`
--

INSERT INTO `client_info` (`client_id`, `client_username`, `age`, `height`, `weight`, `past_injuries`, `health_issues`, `days_per_week`, `hours_per_day`, `training_preference`, `objectives`) VALUES
('1J7YNX', 'charbel2', '25', '170', '80', 'Broken Elbow', 'Diabetes', '4', '2', 'Powerlifting', 'Lift 200Kg'),
('8SQ6XW', 'JamilAwada123', '20', '190', '36', 'Broken Back', 'None', '5', '2', 'light', 'the olympia '),
('IT6J7R', 'mdhaini2', '20', '12', '19', 'Broken Nose', 'Anger issues', '2', '9', 'Powerlifting', 'Lift 250kg'),
('X4PHWB', 'alichehade', '0', '173', '80', 'Broken elbow', 'None', '3', '3', 'Body Building', 'Be like Cbum');

-- --------------------------------------------------------

--
-- Table structure for table `client_questions`
--

CREATE TABLE `client_questions` (
  `question` varchar(200) NOT NULL,
  `trainer_username` varchar(30) NOT NULL,
  `client_id` varchar(6) NOT NULL,
  `answer` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `exercise`
--

CREATE TABLE `exercise` (
  `exercise_id` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `exercise_name` varchar(30) NOT NULL,
  `comments` text,
  `client_feedback` text,
  `workout_id` varchar(6) NOT NULL,
  `position` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `exercise`
--

INSERT INTO `exercise` (`exercise_id`, `exercise_name`, `comments`, `client_feedback`, `workout_id`, `position`) VALUES
('4S0N3Q', 'Bench Press', 'Goooo', 'Easy ', 'K3CMN8', 0),
('CLQHY0', 'Shoulder Press', 'Focus on mind muscle connection', 'Easyy', 'LPTF4Y', 0),
('H2RV67', 'Dumbell Shoulder Press', 'Go as Explosive as you can\n', 'lemon squeezy', 'LPTF4Y', 1),
('TK2XWN', 'Pull Ups', 'Focus on mind muscle connection', 'No Troubles', 'K3CMN8', 1);

-- --------------------------------------------------------

--
-- Table structure for table `login_trainer_client`
--

CREATE TABLE `login_trainer_client` (
  `plan_id` varchar(6) NOT NULL,
  `client_id` varchar(6) NOT NULL,
  `client_fullname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `trainer_username` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `login_trainer_client`
--

INSERT INTO `login_trainer_client` (`plan_id`, `client_id`, `client_fullname`, `trainer_username`) VALUES
('0X98TA', 'IT6J7R', 'Abdallah Dhaini', 'mdhaini10'),
('PQ7CR6', '1J7YNX', 'Charbel Daoud', 'mdhaini10');

-- --------------------------------------------------------

--
-- Table structure for table `sets`
--

CREATE TABLE `sets` (
  `set_id` varchar(6) NOT NULL,
  `set_name` varchar(30) NOT NULL,
  `exercise_id` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `reps` varchar(30) DEFAULT NULL,
  `client_reps` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0',
  `weight` varchar(30) DEFAULT NULL,
  `client_weight` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0',
  `complete` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `sets`
--

INSERT INTO `sets` (`set_id`, `set_name`, `exercise_id`, `reps`, `client_reps`, `weight`, `client_weight`, `complete`) VALUES
('93GWQI', 'Paused Set', 'H2RV67', '10', '0', '100KG', '0', '0'),
('GI0S9W', 'Working Set 1', 'H2RV67', '15', '0', '100kg', '0', '0'),
('K3TXZC', 'Top Set', 'TK2XWN', '1', '2', '130kg', '130kg', '0'),
('Q5ZI47', 'Top Set', '4S0N3Q', '18', '19', '131kg', '131kg', '1');

-- --------------------------------------------------------

--
-- Table structure for table `trainer`
--

CREATE TABLE `trainer` (
  `username` varchar(30) NOT NULL,
  `full_name` varchar(50) NOT NULL,
  `dob` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` varchar(10) NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `register_date` date NOT NULL,
  `password` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `trainer`
--

INSERT INTO `trainer` (`username`, `full_name`, `dob`, `gender`, `email`, `register_date`, `password`) VALUES
('fadiAmad10', 'Fadi Amad', '21 - 4 - 2001', 'Male', 'fadi.amad@lau.edu', '2022-04-21', '$2y$10$8iQ8SL75KddPWgtNpjndjuW1/JNtWe1BEZwtFGZjWGX234KbnFND.'),
('mdhaini10', 'Mohamad Dhaini', '17 - 9 - 2002', 'Male', 'mohamad.dhaini02@lau.edu', '2022-04-06', '$2y$10$WnBLI0EFVC4.bttYltjmUuqXyjmkzm66mCmAm31Pv92fxl.013/TG');

-- --------------------------------------------------------

--
-- Table structure for table `workout`
--

CREATE TABLE `workout` (
  `workout_id` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `workout_name` varchar(30) NOT NULL,
  `plan_id` varchar(6) NOT NULL,
  `position` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `workout`
--

INSERT INTO `workout` (`workout_id`, `workout_name`, `plan_id`, `position`) VALUES
('DRQWBH', 'Lower Day', 'PQ7CR6', 1),
('K3CMN8', 'Push Day', '0X98TA', 0),
('LPTF4Y', 'Upper Day', 'PQ7CR6', 0),
('N58DQ1', 'Pull Day', '0X98TA', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`username`),
  ADD KEY `client_id` (`client_id`);

--
-- Indexes for table `client_info`
--
ALTER TABLE `client_info`
  ADD PRIMARY KEY (`client_id`),
  ADD KEY `client_username` (`client_username`);

--
-- Indexes for table `client_questions`
--
ALTER TABLE `client_questions`
  ADD PRIMARY KEY (`question`),
  ADD KEY `client_id` (`client_id`),
  ADD KEY `trainer_username` (`trainer_username`);

--
-- Indexes for table `exercise`
--
ALTER TABLE `exercise`
  ADD PRIMARY KEY (`exercise_id`),
  ADD KEY `workout_id` (`workout_id`);

--
-- Indexes for table `login_trainer_client`
--
ALTER TABLE `login_trainer_client`
  ADD PRIMARY KEY (`plan_id`),
  ADD KEY `client_id` (`client_id`),
  ADD KEY `trainer_username` (`trainer_username`);

--
-- Indexes for table `sets`
--
ALTER TABLE `sets`
  ADD PRIMARY KEY (`set_id`),
  ADD KEY `exercise_id` (`exercise_id`);

--
-- Indexes for table `trainer`
--
ALTER TABLE `trainer`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `workout`
--
ALTER TABLE `workout`
  ADD PRIMARY KEY (`workout_id`),
  ADD KEY `plan_id` (`plan_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `client_info`
--
ALTER TABLE `client_info`
  ADD CONSTRAINT `client_info_ibfk_1` FOREIGN KEY (`client_username`) REFERENCES `client` (`username`);

--
-- Constraints for table `client_questions`
--
ALTER TABLE `client_questions`
  ADD CONSTRAINT `client_questions_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client_info` (`client_id`),
  ADD CONSTRAINT `client_questions_ibfk_2` FOREIGN KEY (`trainer_username`) REFERENCES `trainer` (`username`);

--
-- Constraints for table `exercise`
--
ALTER TABLE `exercise`
  ADD CONSTRAINT `exercise_ibfk_3` FOREIGN KEY (`workout_id`) REFERENCES `workout` (`workout_id`);

--
-- Constraints for table `login_trainer_client`
--
ALTER TABLE `login_trainer_client`
  ADD CONSTRAINT `login_trainer_client_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client_info` (`client_id`),
  ADD CONSTRAINT `login_trainer_client_ibfk_2` FOREIGN KEY (`trainer_username`) REFERENCES `trainer` (`username`);

--
-- Constraints for table `sets`
--
ALTER TABLE `sets`
  ADD CONSTRAINT `sets_ibfk_3` FOREIGN KEY (`exercise_id`) REFERENCES `exercise` (`exercise_id`);

--
-- Constraints for table `workout`
--
ALTER TABLE `workout`
  ADD CONSTRAINT `workout_ibfk_1` FOREIGN KEY (`plan_id`) REFERENCES `login_trainer_client` (`plan_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
