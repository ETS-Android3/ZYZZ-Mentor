<?php
include("db_info.php");

$workoutID = $_POST['workoutID'];

// Check if the plan_id exist or not
$workoutsQuery = $mysqli->query("SELECT * FROM exercise where workout_id='$workoutID'");
if($workoutsQuery->num_rows==0){
    exit("PLease Add an Exercise");
}

// Fetch every exercise belonging to this workout
while($row =mysqli_fetch_assoc($workoutsQuery))
{
    // Fetch every sets belonging to this exercise
    $exerciseID = $row['exercise_id'];
    $setsQuery = $mysqli->query("SELECT * FROM sets where exercise_id='$exerciseID'");
    
    // If there is no sets assigned
    if($setsQuery->num_rows==0){
        $sets = array("set_name"=>null);
        array_push($row,$sets);
        $emparray[] = $row; 
    }

    // If there is sets assigned
    else{
        while($row2 = mysqli_fetch_assoc($setsQuery)){
            $setsArr[] = $row2;
        }
        // adding the setsArr to the exercise jsonObject in form of jsonArray
        array_push($row,$setsArr);
        
        // Reseting the setsArr array to default
        unset($setsArr);
        
        $emparray[] = $row;
          
    }
    
}
echo json_encode($emparray);