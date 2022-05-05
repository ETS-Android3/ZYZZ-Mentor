<?php 
include("db_info.php");

// Get the data from user
$username = $_POST['username'];
$height = $_POST['height'];
$weight = $_POST['weight'];
$pastInjuries = $_POST['pastInjuries'];
$healthIssues = $_POST['healthIssues'];
$daysPerWeek = $_POST['daysPerWeek'];
$hoursPerDay = $_POST['hoursPerDay'];
$trainingPreference = $_POST['trainingPreference'];
$objectives = $_POST['objectives'];

// Calculate the age of the client
$userDOBQuery = $mysqli->prepare("SELECT dob from client where username='$username'");
$userDOBQuery->execute();

$fetchDOB = mysqli_fetch_assoc($userDOBQuery->get_result());
$DOB = $fetchDOB['dob'];
$splitDOB = explode("-", $DOB);
$yearBorn = $splitDOB[2];
$age = date("Y")- $yearBorn;

// Check if the user already registered his personal info
$usernameQuery = $mysqli->prepare("SELECT client_username from client_info where client_username='$username'");
$usernameQuery->execute();

if($usernameQuery->get_result()->num_rows>0){
    // If yes we update his information with the data given above
    $updateClientInfo = $mysqli->prepare("UPDATE client_info SET age='$age',height='$height', weight='$weight', past_injuries= '$pastInjuries', health_issues='$healthIssues',
    days_per_week='$daysPerWeek',hours_per_day='$hoursPerDay',training_preference='$trainingPreference',objectives='$objectives' WHERE client_username='$username'");
    $updateClientInfo->execute();
    if($updateClientInfo){
        echo "Update complete!";
    }
}
else{
    // Create a new client_id for the user for security reasons.
    $id = gen_id(6,$mysqli);

    // Register client info to the database.
    $registerClientInfoQuery =   $mysqli->prepare("INSERT INTO client_info (client_id,client_username,age,height,weight,past_injuries,health_issues,days_per_week,hours_per_day,training_preference,objectives) VALUES('$id','$username',
    '$age','$height','$weight','$pastInjuries','$healthIssues','$daysPerWeek','$hoursPerDay','$trainingPreference','$objectives')"); 
    $registerClientInfoQuery->execute();

    if($registerClientInfoQuery){
        // add clientID to client table
        $insertClientIDQuery = $mysqli->prepare("UPDATE client set client_id = '$id' WHERE username = '$username'");
        $insertClientIDQuery->execute();
        echo "Registration complete!";
    }


}

function gen_id($l,$mysqli){
    $generatedID = substr(str_shuffle("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, $l);
    $clientIdQuery = $mysqli->query("SELECT client_id from client_info where client_id='$generatedID'");
    // To make sure the id generated is not already used.
    while($clientIdQuery->num_rows>0){
        $generatedID = substr(str_shuffle("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, $l);
        $clientIdQuery = $mysqli->query("SELECT id from client where client_id='$generatedID'");
    }
    return $generatedID;
}
?>