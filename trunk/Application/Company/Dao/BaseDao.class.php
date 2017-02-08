<?php
namespace Company\Dao;
use Think\Log;

use Home\Service\SysEnum;

class BaseDao{
    
    protected $db = null;
    
    //从外部注入db，支持事务
    public function __construct($db) {
        
        $this->db = $db; 
    }
    
    // 跟踪日志
    protected function trackLog($title, $content, $source = 0, $type = "DEBUG") {

        if(!is_string($content)) {
            $content = json_encode($content);
        }

        $_prefix = "[ selfdriving ]";
        $_date = "[ " . date("H:i:s", time()) . " ]";
        $title = "[ " . $title . " ]";

        if($source == SysEnum::TrackSource) {

            $source = I('source', 0, 'intval');
            Log::record($_prefix ." ". $_date ." ". $title . "source::" . $source . "::" . __ACTION__ , $type);

        } else {
            Log::record($_prefix ." ". $_date ." ". $title . $content, $type);
        }
    }
}
?>
