<!DOCTYPE html>
<!--
* Michael Kochell
* Section 2
* Recreation of a "Rotten Tomatoes" spoof page called "Rancid Tomatoes"
* This file contains the main content and html tags, along with php.
-->
<html>
	<head>
		<title>Rancid Tomatoes</title>
		<link rel="shortcut icon" type="image/png" href="rotten.gif"/>
		<meta charset="utf-8" />
		<link href="movie.css" type="text/css" rel="stylesheet" />
	</head>

	<body>

		<?php
		$movie = $_GET["film"];
		$mainInfo = getTitleYearRating($movie);
		$overviewInfo = getOverviewInfo($movie);
		$reviews = getReviews($movie);
		$numReviews = count($reviews);
		$overviewImage = "./" . $movie . "/overview.png";
		$reviewStringArray = getColumnReviews($reviews);
		$firstColumnReviews = $reviewStringArray[0];
		$secondColumnReviews = $reviewStringArray[1];
		$bigImageString = "";
		if ($mainInfo["rating"] < 60) {
			$bigImageString = "<img src=\"rottenbig.png\" alt=\"Rotten\" />";
		} else {
			$bigImageString = "<img src=\"freshbig.png\" alt=\"Fresh\" />";
		}
		?>

		<div id="banner">
			<img src="banner.png" alt="Rancid Tomatoes" />
		</div>

		<h1> <?= $mainInfo["title"] . " (" . $mainInfo["year"] . ")" ?>
		</h1>

		<div id="main_content">

			<div id="general_overview" class="green">
				<div>
					<img src=
					"<?= $overviewImage ?>"
					alt="general overview" />
				</div>
				<dl>
					<?php
					foreach ($overviewInfo as $term => $definition) {
						print("<dt>" . $term . "</dt>");
						print("<dd>" . $definition . "</dd>");
					}
					?>
				</dl>
			</div>
			<div id="overall_left">
				<div>
				<?= $bigImageString ?>
				<span> <?= $mainInfo["rating"] ?>%</span>
				</div>
				<div id="reviews">
					<div class="column">
						<?= $firstColumnReviews ?>
					</div>
					<div class="column">
						<?= $secondColumnReviews ?>
					</div>
				</div>
			</div>
			<p class ="green">
				(1-
				<?= $numReviews ?>) of
				<?= $numReviews ?>
			</p>
		</div>

		<div>
			<a href="https://webster.cs.washington.edu/validate-html.php"><img src="http://webster.cs.washington.edu/w3c-html.png" alt="Valid HTML5" /></a>
			<br />
			<a href="https://webster.cs.washington.edu/validate-css.php"><img src="http://webster.cs.washington.edu/w3c-css.png" alt="Valid CSS" /></a>
		</div>

		<?php

		function getTitleYearRating($movie) {
			$fp = fopen("./" . $movie . "/info.txt", "r");
			$array = array();
			if ($fp) {
				$title = fgets($fp);
				$year = fgets($fp);
				$rating = fgets($fp);
				$array["title"] = $title;
				$array["year"] = trim($year);
				$array["rating"] = $rating;

			} else {
				print("title broken <br />");
			}
			fclose($fp);

			return $array;
		}

		function getOverviewInfo($movie) {
			$fp = fopen("./" . $movie . "/overview.txt", "r");
			$array = array();
			if ($fp) {
				while (($line = fgets($fp)) !== false) {
					$subarray = explode(':', $line);
					$array[$subarray[0]] = trim($subarray[1]);
				}
			} else {
				print("overview broken <br />");
			}
			fclose($fp);
			return $array;
		}

		function getReviews($movie) {

			$files = getReviewFiles($movie);
			$numFiles = count($files);
			$doubleDigits = $numFiles > 9;

			$prefix = "./" . $movie . "/review";
			$masterArray = array();
			for ($i = 1; $i <= $numFiles; $i++) {
				$currentFile = $prefix;
				if ($doubleDigits && $i < 10) {
					$currentFile = $currentFile . 0;
				}
				$currentFile = $currentFile . $i . ".txt";
				$fp = fopen($currentFile, "r");
				$array = array();
				if ($fp) {
					$text = fgets($fp);
					$freshness = fgets($fp);
					$name = fgets($fp);
					$publisher = fgets($fp);
					$array["text"] = trim($text);
					$array["freshness"] = trim($freshness);
					$array["name"] = trim($name);
					$array["publisher"] = trim($publisher);
					$masterArray[$i] = $array;
				} else {
					print("reviews broken <br />");
				}
				fclose($fp);
			}
			return $masterArray;
		}

		function getReviewFiles($movie) {

			$dir = "./" . $movie;
			$path = $dir . "/review*.txt";

			$files = glob($path);
			return $files;
		}

		function getColumnReviews($reviews) {
			$numReviews = count($reviews);
			$numToPrintLeft = 0;
			if ($numReviews % 2 === 0) {
				$numToPrintLeft = $numReviews / 2;
			} else {
				$numToPrintLeft = floor($numReviews / 2) + 1;
			}
			$array = array();
			$firstColumnString = "";
			$secondColumnString = "";
			$image = "";
			$alt = "";
			$i = 1;
			for ($i = 1; $i <= $numToPrintLeft; $i++) {
				$reviewString = getReviewString($reviews, $i);
				$firstColumnString = $firstColumnString . $reviewString;
			}
			for (; $i <= $numReviews; $i++) {
				$reviewString = getReviewString($reviews, $i);
				$secondColumnString = $secondColumnString . $reviewString;
			}

			$array[0] = $firstColumnString;
			$array[1] = $secondColumnString;
			return $array;
		}

		function getReviewString($reviews, $i) {
			$image = "";
			$alt = "";
			if ($reviews[$i]["freshness"] === "FRESH") {
				$image = "fresh.gif";
				$alt = "Fresh";
			} else {
				$image = "rotten.gif";
				$alt = "Rotten";
			}
			$returnString = "<div> <p> <img src=\"" . $image . "\" alt=\"" . $alt . "\" />";

			$returnString = $returnString . "<q>" . $reviews[$i]["text"] . "</q></p>";

			$returnString = $returnString . "<p> <img src =\"critic.gif\" alt=\"Critic\" />" . $reviews[$i]["name"] . "<br />" . $reviews[$i]["publisher"] . "</p> </div>";

			return $returnString;
		}
		?>
	</body>
</html>
