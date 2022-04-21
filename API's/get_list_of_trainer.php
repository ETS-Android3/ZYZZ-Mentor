<?php 
include("db_info.php");

// Get the data from user


// Calculate the age of the client
$trainerUsernameQuery = $mysqli->query("SELECT username, full_name from trainer");

while($row =mysqli_fetch_assoc($trainerUsernameQuery))
{
    $emparray[] = $row;
}
echo json_encode($emparray);

?>