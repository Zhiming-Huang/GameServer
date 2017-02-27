<?php

namespace dao;

use DbHelper;

require_once __DIR__ . '/../db/DbHelper.php';

class ItemDao{
    
    public function queryItems($cityName, $roomName){
        
        $db = new DbHelper();
        $querySql = "SELECT * FROM item WHERE cityName='$cityName' AND roomName = '$roomName'";
        $result = $db->query($querySql);
        if(!$result){
            
            return array();
        }
        
        return $result;
    }
    
    public function queryItem($cityName, $roomName, $name){
        
        $db = new DbHelper();
        $querySql = "SELECT * FROM item WHERE cityName='$cityName' AND roomName = '$roomName' AND name = '$name'";
        echo $querySql . "\n";
        $result = $db->query($querySql);
        if(!$result){
            
            return array();
        }
        
        return $result[0];    
    }
}

?>
