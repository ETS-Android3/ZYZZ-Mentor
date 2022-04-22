<?php 
include("db_info.php");

// Get the data from user
$clientID = $_POST['clientID'];



$trainerUserNameQuery = $mysqli->query("SELECT trainer_username from login_trainer_client where client_id='$clientID'");

if($trainerUserNameQuery->num_rows==0){
    exit("No trainer");
}

$fetchTrainerUsername = mysqli_fetch_assoc($trainerUserNameQuery);
$trainerUsername = $fetchTrainerUsername['trainer_username'];
$trainerNameQuery = $mysqli->query("SELECT full_name from trainer where username='$trainerUsername'");
$fetchTrainerName = mysqli_fetch_assoc($trainerNameQuery);
$trainerName = $fetchTrainerName['full_name'];
echo $trainerName;




?>