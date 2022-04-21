<?php 
include("db_info.php");

// Get the data from user
$clientID = $_POST['clientID'];



$planIDQuery = $mysqli->query("SELECT plan_id from login_trainer_client where client_id='$clientID'");

if($planIDQuery->num_rows==0){
    exit("You are not subscribed with any trainer");
}

$fetchPlanID = mysqli_fetch_assoc($planIDQuery);
$planID = $fetchPlanID['plan_id'];

$workoutIDQuery = $mysqli->query("SELECT workout_id from workout where plan_id='$planID'");

while($row =mysqli_fetch_assoc($workoutIDQuery))
{
  
    $exerciseIDQuery = $mysqli->query("SELECT exercise_id from exercise where workout_id='$row[workout_id]'");

    while($row2 =mysqli_fetch_assoc($exerciseIDQuery))
    {
    
        
        $deletesetsQuery = $mysqli->query("DELETE from sets where exercise_id='$row2[exercise_id]'");
        $deleteExerciseQuery = $mysqli->query("DELETE from exercise where exercise_id='$row2[exercise_id]'");
    }
    $deleteWorkoutQuery = $mysqli->query("DELETE from workout where workout_id='$row[workout_id]'");
}



$trainerUserNameQuery = $mysqli->query("DELETE from login_trainer_client where client_id='$clientID'");

echo "Subscription with trainer ended! \n search for another";




?>