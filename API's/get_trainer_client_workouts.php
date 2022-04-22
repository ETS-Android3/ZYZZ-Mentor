<?php 
include("db_info.php");

$planID = $_GET["planID"];
$planIDQuery = $mysqli->query("SELECT * FROM workout WHERE plan_id = '$planID'");

while($row =mysqli_fetch_assoc($planIDQuery))
{
    $emparray[] = $row;
}
echo json_encode($emparray);

?>