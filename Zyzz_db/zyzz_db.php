<?php
/**
 * Export to PHP Array plugin for PHPMyAdmin
 * @version 5.1.1
 */

/**
 * Database `zyzz_db`
 */

/* `zyzz_db`.`client` */
$client = array(
  array('username' => 'alichehade','full_name' => 'Ali Chehade','password' => '$2y$10$WTErLf/RkuR9rIgsR2aU8ewywFAok.mTVsoCAdc8aftBjHU5GALLW','email' => 'jamil.awada@lau.edu','dob' => '21 - 4 - 2022','gender' => 'Male','register_date' => '2022-04-21','client_id' => 'X4PHWB'),
  array('username' => 'charbel2','full_name' => 'Charbel Daoud','password' => '$2y$10$eOa0XiZ2PmTwCbLrp5NwNe.P01NrNr8JphzH9VNzvfEHbqWb/S1aS','email' => 'mohamad.dhaini02@lau.edu','dob' => '3 - 7 - 1997','gender' => 'Male','register_date' => '2022-05-03','client_id' => '1J7YNX'),
  array('username' => 'JamilAwada123','full_name' => 'Jamil Awada','password' => '$2y$10$0QkHLRX69SmaHPCRlRJTUetzpQHNQ6arbNGxxU8OmylxlLlneJn9m','email' => 'jamilawada@gmail.com','dob' => '24 - 5 - 2002','gender' => 'Male','register_date' => '2022-05-04','client_id' => '8SQ6XW'),
  array('username' => 'mdhaini2','full_name' => 'Abdallah Dhaini','password' => '$2y$10$gGqJgLPE6nqnaEwsaAK36.WaSQfMvs/oLRgIOL4c3qii8sNVzWcu6','email' => 'mohamad.dhaini@lau.edu','dob' => '17-9-2002','gender' => 'Male','register_date' => '2022-04-07','client_id' => 'IT6J7R')
);

/* `zyzz_db`.`client_info` */
$client_info = array(
  array('client_id' => '1J7YNX','client_username' => 'charbel2','age' => '25','height' => '170','weight' => '80','past_injuries' => 'Broken Elbow','health_issues' => 'Diabetes','days_per_week' => '4','hours_per_day' => '2','training_preference' => 'Powerlifting','objectives' => 'Lift 200Kg'),
  array('client_id' => '8SQ6XW','client_username' => 'JamilAwada123','age' => '20','height' => '190','weight' => '36','past_injuries' => 'Broken Back','health_issues' => 'None','days_per_week' => '5','hours_per_day' => '2','training_preference' => 'light','objectives' => 'the olympia '),
  array('client_id' => 'IT6J7R','client_username' => 'mdhaini2','age' => '20','height' => '12','weight' => '19','past_injuries' => 'Broken Nose','health_issues' => 'Anger issues','days_per_week' => '2','hours_per_day' => '9','training_preference' => 'Powerlifting','objectives' => 'Lift 250kg'),
  array('client_id' => 'X4PHWB','client_username' => 'alichehade','age' => '0','height' => '173','weight' => '80','past_injuries' => 'Broken elbow','health_issues' => 'None','days_per_week' => '3','hours_per_day' => '3','training_preference' => 'Body Building','objectives' => 'Be like Cbum')
);

/* `zyzz_db`.`client_questions` */
$client_questions = array(
);

/* `zyzz_db`.`exercise` */
$exercise = array(
  array('exercise_id' => '4S0N3Q','exercise_name' => 'Bench Press','comments' => 'Goooo','client_feedback' => 'Easy ','workout_id' => 'K3CMN8','position' => '0'),
  array('exercise_id' => 'CLQHY0','exercise_name' => 'Shoulder Press','comments' => 'Focus on mind muscle connection','client_feedback' => 'Easyy','workout_id' => 'LPTF4Y','position' => '0'),
  array('exercise_id' => 'H2RV67','exercise_name' => 'Dumbell Shoulder Press','comments' => 'Go as Explosive as you can
','client_feedback' => 'lemon squeezy','workout_id' => 'LPTF4Y','position' => '1'),
  array('exercise_id' => 'TK2XWN','exercise_name' => 'Pull Ups','comments' => 'Focus on mind muscle connection','client_feedback' => 'No Troubles','workout_id' => 'K3CMN8','position' => '1')
);

/* `zyzz_db`.`login_trainer_client` */
$login_trainer_client = array(
  array('plan_id' => '0X98TA','client_id' => 'IT6J7R','client_fullname' => 'Abdallah Dhaini','trainer_username' => 'mdhaini10'),
  array('plan_id' => 'PQ7CR6','client_id' => '1J7YNX','client_fullname' => 'Charbel Daoud','trainer_username' => 'mdhaini10')
);

/* `zyzz_db`.`sets` */
$sets = array(
  array('set_id' => '93GWQI','set_name' => 'Paused Set','exercise_id' => 'H2RV67','reps' => '10','client_reps' => '0','weight' => '100KG','client_weight' => '0','complete' => '0'),
  array('set_id' => 'GI0S9W','set_name' => 'Working Set 1','exercise_id' => 'H2RV67','reps' => '15','client_reps' => '0','weight' => '100kg','client_weight' => '0','complete' => '0'),
  array('set_id' => 'K3TXZC','set_name' => 'Top Set','exercise_id' => 'TK2XWN','reps' => '1','client_reps' => '2','weight' => '130kg','client_weight' => '130kg','complete' => '0'),
  array('set_id' => 'Q5ZI47','set_name' => 'Top Set','exercise_id' => '4S0N3Q','reps' => '18','client_reps' => '19','weight' => '131kg','client_weight' => '131kg','complete' => '1')
);

/* `zyzz_db`.`trainer` */
$trainer = array(
  array('username' => 'fadiAmad10','full_name' => 'Fadi Amad','dob' => '21 - 4 - 2001','gender' => 'Male','email' => 'fadi.amad@lau.edu','register_date' => '2022-04-21','password' => '$2y$10$8iQ8SL75KddPWgtNpjndjuW1/JNtWe1BEZwtFGZjWGX234KbnFND.'),
  array('username' => 'mdhaini10','full_name' => 'Mohamad Dhaini','dob' => '17 - 9 - 2002','gender' => 'Male','email' => 'mohamad.dhaini02@lau.edu','register_date' => '2022-04-06','password' => '$2y$10$WnBLI0EFVC4.bttYltjmUuqXyjmkzm66mCmAm31Pv92fxl.013/TG')
);

/* `zyzz_db`.`workout` */
$workout = array(
  array('workout_id' => 'DRQWBH','workout_name' => 'Lower Day','plan_id' => 'PQ7CR6','position' => '1'),
  array('workout_id' => 'K3CMN8','workout_name' => 'Push Day','plan_id' => '0X98TA','position' => '0'),
  array('workout_id' => 'LPTF4Y','workout_name' => 'Upper Day','plan_id' => 'PQ7CR6','position' => '0'),
  array('workout_id' => 'N58DQ1','workout_name' => 'Pull Day','plan_id' => '0X98TA','position' => '1')
);
