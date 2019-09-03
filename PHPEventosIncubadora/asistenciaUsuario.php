<?php
  require_once("conexionBD.php");

  $hash = $_GET["hash"];
  $idEvento = $_GET["evento"];

  $sql = "SELECT id_usuario FROM Usuario WHERE hash = '$hash'";

  try {
    $result = $conn -> query($sql);
    $id = $result->fetch_array();
    $idUsuario = $id["id_usuario"];
    $idEvento = 1;
  } catch (Exception $e) {
    $respuesta = array(
      "error" => $e->getMessage()
    );
    echo json_encode($respuesta);
  }

  try {

    $stmt = $conn -> prepare("INSERT INTO Asistencia (id_evento, id_usuario) VALUES (?,?)");
    $stmt -> bind_param("ii", $idEvento, $idUsuario);
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
