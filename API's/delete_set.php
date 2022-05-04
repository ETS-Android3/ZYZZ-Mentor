<?php 
include("db_info.php");

$setID = $_GET['setID'];

// Not prone to SQL Injection
$setIDQuery = $mysqli->query("SELECT set_id from sets where set_id='$setID'");

if($setIDQuery->num_rows==0){
    exit("Nothing to delete");
}

$deleteWorkoutQuery = $mysqli->query("DELETE from sets where set_id='$setID'");

echo "Set Deleted!";
