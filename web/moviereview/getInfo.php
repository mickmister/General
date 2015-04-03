<!doctype html>
<html lang="en">
	<head>
		<meta charzet="utf-8">
		<title>Page title</title>
	</head>
	<body>
		<?php
		$movie = $_GET["film"];
		function getTitleYearRating()
		{
			global $movie;
			$fp = fopen("./" . $movie . "/info.txt", "r");
			$array = array();
			if ($fp) 
			{
				$title = fgets($fp);
				$year = fgets($fp);
				$rating = fgets($fp);	
				$array["title"] = $title;
				$array["year"] = $year;
				$array["rating"] = $rating;
					
			} 
			else 
			{
				print("title broken <br />");
			} 
			fclose($fp);
			
			return $array;
		}
		
		$array = getTitleYearRating();
		
		print("Title: " . $array["title"] . "<br />");
		print("Year: " . $array["year"] . "<br />");
		print("Rating: " . $array["rating"] . "<br />");
		print(count($array) . "<br />");
		
		
		function getOverviewInfo()
		{
			global $movie;
			$fp = fopen("./" . $movie . "/overview.txt", "r");
			$array = array();
			if ($fp) 
			{
				while (($line = fgets($fp)) !== false) 
				{
    				$subarray = explode(':', $line);
					$array[$subarray[0]] = $subarray[1];				
				}
			}
			else
			{
				print("overview broken <br />");			
			}
			fclose($fp);
			return $array;
		}
		
		
		print("<br /><br />");
		
		$array2 = getOverviewInfo();
		
		foreach ($array2 as $key => $value)
		{
			print($key . ": " . $value . "<br />");		
		}
		
		
		function getReviews()
		{
			global $movie;
			
			$files = getReviewFiles();
			$numFiles = count($files);
			$doubleDigits = $numFiles > 9;
			
			$prefix = "./" . $movie . "/review";
			print($numFiles . " This many<br />");
			$masterArray = array();
			for($i=1; $i<=$numFiles; $i++)
			{
				$currentFile = $prefix;
				if($doubleDigits && $i < 10)
				{
					$currentFile = $currentFile . 0;
				}
				$currentFile = $currentFile . $i . ".txt";
				$fp = fopen($currentFile, "r");
				$array = array();
				if ($fp) 
				{
					$text = fgets($fp);
					$freshness = fgets($fp);
					$name = fgets($fp);
					$publisher = fgets($fp);
					$array["text"] = $text;
					$array["freshness"] = $freshness;
					$array["name"] = $name;
					$array["publisher"] = $publisher;
					$masterArray[$i] = $array;
				}
				else
				{
					print("reviews broken <br />");			
				}
				fclose($fp);
			}
				return $masterArray;
		}
		
		function getReviewFiles()
		{
			global $movie;
			
			$dir = "./" . $movie;
			$path = $dir . "/review*.txt";
			
			$files = glob($path);
			return $files;
		}
		
		$reviews = getReviews();
		print("Count = " . count($reviews) . "<br />");
		
		
		function getColumnReviews()
		{
			global $reviews;
			$numReviews = count($reviews);
			$numToPrintLeft = 0;
			if($numReviews % 2 === 0)
			{
				$numToPrintLeft = $numReviews / 2;
			}
			else
			{
				$numToPrintLeft = $numReviews / 2 + 1;
			}
			$array = array();
			$firstColumnString = "";
			$secondColumnString = "";
			$image = "";
			$alt = "";
			$i = 1;
			for($i = 1; $i <= $numToPrintLeft; $i++)
			{
			    $reviewString = getReviewString($i);
				$firstColumnString = $firstColumnString . $reviewString;			
			}
			for($i = $numToPrintLeft + 1; $i <= $numReviews; $i++)
			{
			    $reviewString = getReviewString($i);
				$secondColumnString = $secondColumnString . $reviewString;			
			}
			
			$array[0] = $firstColumnString;
			$array[1] = $secondColumnString;
			return $array;
		}
		
		function getReviewString($i)
		{
			global $reviews;
			$image = "";
			$alt = "";
			if($reviews[$i]["freshness"] === "FRESH")
			{
				$image = "fresh.gif";
				$alt = "Fresh";
			}
			else
			{
				$image = "rotten.gif";
				$alt = "Rotten";
			}
			$returnString = "<div> <p> <img src=\""
			. $image .
			"\"alt=\"" . 
			$alt . "\" />";
			
			$returnString = $returnString . "<q>" . 
			$reviews[$i]["text"] . "</q></p>";
			
			$returnString = $returnString .
			"<p> <img src =\"critic.gif\" alt=\"Critic\" />" .
			$reviews[$i]["name"] . 
			"<br />" . 
			$reviews[$i]["publisher"] . 
			"</p> </div>";
			
			return $returnString;
		}
		
		$reviewArray = getColumnReviews();
		print($reviewArray[0] . "<br />");
			
		?>
	</body>		
</html>