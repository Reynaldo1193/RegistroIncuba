<?php

$servername = "www.racni.com";
$username = "qrcodes";
$password = "F354c4tl4n";
$dbname = "1ncub4d0r4";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

 ?>
