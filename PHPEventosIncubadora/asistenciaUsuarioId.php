<?php
  require_once("conexionBD.php");

  $id = $_GET["id"];
  $idEvento = $_GET["evento"];  

  try {

    $stmt = $conn -> prepare("INSERT INTO Asistencia (id_evento, id_usuario) VALUES (?,?)");
    $stmt -> bind_param("ii", $idEvento, $id);
    $stmt -> execute();

    $respuesta = array(
      "respuesta" => $conn -> error
    );

  } catch (Exception $e) {
    $respuesta = array(
      "error" => $e->getMessage()
    );
  }

  echo json_encode($respuesta);

  $result -> close();
  $conn -> close();
  $stmt -> close();

 ?>
