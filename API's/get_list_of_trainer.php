<?php 
include("db_info.php");

// Not prone to SQL Injection
$trainerUsernameQuery = $mysqli->query("SELECT username, full_name from trainer");

while($row =mysqli_fetch_assoc($trainerUsernameQuery))
{
   
    $emparray[] = $row;
}
echo json_encode($emparray);

?>