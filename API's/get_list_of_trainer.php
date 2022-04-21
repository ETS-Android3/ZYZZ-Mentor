<?php 
include("db_info.php");


$trainerUsernameQuery = $mysqli->query("SELECT username, full_name from trainer");

while($row =mysqli_fetch_assoc($trainerUsernameQuery))
{
   
    $emparray[] = $row;
}
echo json_encode($emparray);

?>