<?php
 //export.php
 if(!empty($_FILES["excel_file"]))
 {
      $connect = mysqli_connect("localhost", "root", "", "Assignment");
      $file_array = explode(".", $_FILES["excel_file"]["name"]);
      if($file_array[1] == "xls")
      {
           include("PHPExcel/IOFactory.php");
           $output = '';
           $output .= "
           <label class='text-success'>Data Inserted</label>
                <table class='table table-bordered'>
                     <tr>
                          <th> Name</th>
                          <th>Age</th>
                          <th>Town</th>

                     </tr>
                     ";
           $object = PHPExcel_IOFactory::load($_FILES["excel_file"]["tmp_name"]);
           foreach($object->getWorksheetIterator() as $worksheet)
           {
                $highestRow = $worksheet->getHighestRow();
                for($row=2; $row<=$highestRow; $row++)
                {
                     $name = mysqli_real_escape_string($connect, $worksheet->getCellByColumnAndRow(1, $row)->getValue());
                     $age = mysqli_real_escape_string($connect, $worksheet->getCellByColumnAndRow(2, $row)->getValue());
                     $town = mysqli_real_escape_string($connect, $worksheet->getCellByColumnAndRow(3, $row)->getValue());

                     $query = "
                     INSERT INTO user
                     (rName, Age, Town)
                     VALUES ('".$name."', '".$age."', '".$town."')
                     ";
                     mysqli_query($connect, $query);
                     $output .= '
                     <tr>
                          <td>'.$name.'</td>
                          <td>'.$age.'</td>
                          <td>'.$toen.'</td>

                     </tr>
                     ';
                }
           }
           $output .= '</table>';
           echo $output;
      }
      else
      {
           echo '<label class="text-danger">Invalid File</label>';
      }
 }
 ?>