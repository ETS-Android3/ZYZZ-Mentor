<?php 
include("db_info.php");

$planID = $_GET["planID"];
$planIDQuery = $mysqli->query("SELECT * FROM workout WHERE plan_id = '$planID'");
$emparray;
while($row =mysqli_fetch_assoc($planIDQuery))
{
    $emparray[] = $row;
}
if(count($emparray)==0){
    exit ("0");
}
echo json_encode($emparray);

?>