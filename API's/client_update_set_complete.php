<?php
include("db_info.php");

$setID = $_GET["setID"];
$complete = $_GET["complete"];


$updateSetInfoQuery = $mysqli->query("UPDATE sets SET complete = '$complete' WHERE set_id = '$setID'");

if($updateSetInfoQuery){
    echo "Done";
}
?>