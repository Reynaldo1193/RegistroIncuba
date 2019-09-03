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

$sql = "SELECT nombre_evento, id_evento FROM Evento WHERE activo = 1";
$result = $conn -> query($sql);
$datos = Array();
    while($row = $result->fetch_array()) {
        $datos[] = array_map("utf8_encode",$row);
    }
echo json_encode($datos);
$result -> close();
$conn -> close();

?>
