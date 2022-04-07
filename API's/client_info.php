<?php 
include("db_info.php");

// Get the data from user
$username = $_POST['username'];
$height = $_POST['height'];
$weight = $_POST['weight'];
$pastInjuries = $_POST['pastInjuries'];
$healthIssues = $_POST['healthIssues'];
$dayPerWeek = $_POST['dayPerWeek'];
$hoursPerWeek = $_POST['hoursPerWeek'];
$trainingPreference = $_POST['trainingPreference'];
$objectives = $_POST['objectives'];

// Calculate the age of the client
$userDOBQuery = $mysqli->query("SELECT dob from client where username='$username'");
$fetchDOB = mysqli_fetch_assoc($userDOBQuery);
$DOB = $fetchDOB['dob'];
$splitDOB = explode("-", $DOB);
$yearBorn = $splitDOB[0];
$age = date("Y")- $yearBorn;

// Check if the user already registered his personal info
$usernameQuery = $mysqli->query("SELECT client_username from client_info where client_username='$username'");

if($usernameQuery->num_rows>0){
    // If yes we update his information with the data given above
    $updateClientInfo = $mysqli->query("UPDATE client_info SET age='$age',height='$height', weight='$weight', past_injuries= '$pastInjuries', health_issues='$healthIssues',
    day_per_week='$dayPerWeek',hours_per_week='$hoursPerWeek',training_preference='$trainingPreference',objectives='$objectives' WHERE client_username='$username'");
    if($updateClientInfo){
        echo "Update complete!";
    }
}
else{
    // Create a new client_id for the user for security reasons.
    $id = gen_id(6);
    $query =   $mysqli->query("INSERT INTO client_info (client_id,client_username,age,height,weight,past_injuries,health_issues,day_per_week,hours_per_week,training_preference,objectives) VALUES('$id','$username',
    '$age','$height','$weight','$pastInjuries','$healthIssues','$dayPerWeek','$hoursPerWeek','$trainingPreference','$objectives')"); 
}
function gen_id($l=6){
    return substr(str_shuffle("0123456789abcdefghijklmnopqrstuvwxyz"), 0, $l);
}
?>