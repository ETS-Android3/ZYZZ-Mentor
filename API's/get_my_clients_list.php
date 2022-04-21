<?php 
include("db_info.php");

$trainerUsername = $_POST["username"];
$trainerClientQuery = $mysqli->query("SELECT * FROM login_trainer_client WHERE trainer_username = '$trainerUsername'");

while($row =mysqli_fetch_assoc($trainerClientQuery))
{
   
    $emparray[] = $row;
}
echo json_encode($emparray);

?>