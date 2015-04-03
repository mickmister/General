<!DOCTYPE html>
<html>
<head>
	<title>290: Can't tune a fish. </title>
	<link rel="stylesheet" type="text/css" href="piano.css">
	<script type="text/javascript"
    	src="http://ajax.googleapis.com/ajax/libs/prototype/1.6.0.2/prototype.js">
    </script>
    <script type="text/javascript" src="tone.js"></script>
    <script type="text/javascript" src="scales.js"></script>
    <script type="text/javascript" src="piano.js"></script>
    <script type="text/javascript" src="tableItems.js"></script>
</head>

<body>
	<?php
		$name = $_REQUEST['prog_name'];
		echo "<h1 id=\"name\" class=\"centered\">$name</h1>";
	?>
	<div id="selectDiv">
		<select id="scaleBox" name="scaleBox">
			<!-- fill this with php/maybe from server? -->
		</select>
		<button id="play">
			Play
		</button>
		<button id="clear">
			Clear
		</button>
	</div>
	<hr>
	<div id="upperarea">
	</div>	<!-- will add header for scale name and provide chords with labels -->
	<div id="twoNoteMatching" class="matching">
		<span id="twoNoteText" class="text">Two Note Matching Chords</span>
			<hr>
		<div id="twoNoteSelector"></div>
	</div>
	<div id="threeNoteMatching" class="matching">
		<span id="threeNoteText" class="text">Three Note Matching Chords</span>
			<hr>
		<div id="threeNoteSelector"></div>
	</div>
	<select id="keyappearance">
		<option>
			None
		</option>
		<option  selected="selected">
			Pentatonic
		</option>
		<option>
			All
		</option>
	</select>
	<div id="piano"></div>
	<div id="chordArea"></div>
	
	<?php
		$userId = $_REQUEST['user_id'];
		if(isset($_REQUEST['prog_id'])){
			$id = $_REQUEST['prog_id'];
			$progression = $_REQUEST['progression'];
		}else{
			$id = "none";
			$progression = "none";
		}
		
		echo "<span id=\"user_id\" class=\"hidden\">$userId</span>";
		echo "<span id=\"prog_id\" class=\"hidden\">$id</span>";
		echo "<span id=\"progression\" class=\"hidden\">$progression</span>";
	?>
	
</body>

</html> 