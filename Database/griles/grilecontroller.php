<?php
require_once("GrileRestHandler.php");
		
$reg = "";
$view = "";
if(isset($_GET["view"]))
	$view = $_GET["view"];

switch($view){

	case "all":
		// to handle REST Url /mobile/list/
		$examRestHandler = new StatusRestHandler();
		$examRestHandler->getAllStatus();
		break;

	case "" :
		//404 - not found;
		break;
}

?>
