<?php
    $uemail = $_GET['uemail'];
    $unick = $_GET['unick'];
    $ulike = $_GET['ulike'];
    $uwrite = $_GET['uwrite'];
    $ureply = $_GET['ureply'];
    $uid = $_GET['uid'];

    if($uemail == '' || $unick == '' || $ulike == '' || $uwrite == '' || $ureply == '' || $uid == '') {
        echo 'please fill all values';
    } else {
        require_once('dbConnect.php');
        $sql = "SELECT * FROM USERTABLE WHERE uemail='$uemail' OR uid='$uid'";
        $check = mysqli_fetch_array(mysql_query($con, $sql));

        if(isset($check)) {
            echo 'email or id already exist.'
        } else {
            $sql = "INSERT INTO USERTABLE(uemail,unick,ulike,uwrite,ureply,uid) VALUES('$uemail','$unick','0','0','0','$uid')";

            if(mysql_query($con, $sql)) {
                echo 'successfully registered.';
            } else {
                echo 'Please try again.';
            }
        }
        mysqli_close($con);
    }
?>

