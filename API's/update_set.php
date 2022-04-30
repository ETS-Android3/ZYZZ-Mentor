<?php
include("db_info.php");

$setID = $_POST["setID"];
$columnToUpdate = $_POST["column"];
$updatedInfo = $_POST["updatedInfo"];


$updateSetInfoQuery = $mysqli->query("UPDATE sets SET $columnToUpdate = '$updatedInfo' WHERE set_id = '$setID'");

if($updateSetInfoQuery){
    echo "Done";
}
?>