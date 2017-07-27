<?php
	$db = new mysqli("kriate.mysql", "kriate_mobapp", "FYfurN-9", "kriate_mobapp");
	$db->set_charset("utf8");
	
	$myArray = Array();
	$res = $db->query("SELECT id, name, desk/*, photo*/ FROM thems");
	
	while($item = $res->fetch_assoc()){
    $myArray[] = $item;                                                           
	}	

	$newArrData = Array();
	foreach($myArray as $key=>$value){
    $newArrData[$key] =  $myArray[$key];
    #$newArrData[$key]['photo'] = base64_encode($myArray[$key]['photo']);
	}

	header('Content-type:application/json;charset=utf-8');
	echo json_encode($newArrData, JSON_UNESCAPED_UNICODE);
#  echo json_last_error();

?>
