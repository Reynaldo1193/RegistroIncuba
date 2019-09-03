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

$sql = "SELECT id_evento, nombre_evento, tipo_evento FROM Evento";
$result = $conn->query($sql);
$datos = Array();

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        
        $datos[] = $row;
    }
} else {
    echo "0 results";
}
echo json_encode($datos);
$conn->close();
?>