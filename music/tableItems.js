function chordAction(){
	var array = this.id.split("-");
	var chord = parseInt(array[0]);
	var inversion = parseInt(array[1]);
	if(parseInt(array[2])){
		playChord(chord, inversion);
	}
	else{
		storeChord(chord, inversion);
	}
}


function oneChordInfo(chordNumber, inversionIndex, table){
	var scaleOffset = Math.floor(scaleNumber / 2);
	var scaleInfo = allChordInfo[chordNumber];
	var baseNumber = (Math.floor(chordNumber/2) - scaleOffset + 12) % 12;
	var label, left, middle, right, leftNum, midNum, rightNum, modifier;
	if(chordNumber % 2 === 0){
		modifier = 4;
	}else{
		modifier = 3;
	}
	var labelStuff = ["Root:", "First Inversion:", "Second Inversion:"];
	var scaleInfoStuff = [scaleInfo.root, scaleInfo.middle, scaleInfo.dominant];
	var scaleNumberStuff = [baseNumber % 12, (baseNumber + modifier) % 12, (baseNumber + 7) % 12];
	
	label = labelStuff[inversionIndex];
	left = scaleInfoStuff[(0 +  inversionIndex) % 3];
	middle = scaleInfoStuff[(1 +  inversionIndex) % 3];
	right = scaleInfoStuff[(2 +  inversionIndex) % 3];
	leftNum = scaleNumberStuff[(0 +  inversionIndex) % 3];
	midNum = scaleNumberStuff[(1 +  inversionIndex) % 3];
	rightNum = scaleNumberStuff[(2 +  inversionIndex) % 3];
	

	row = [];
	var cell1 = {};
	cell1.className = "left";
	cell1text.label = label;
	var cell2 = {};
	cell2.className = "center";
	cell2text.label = left;
	var cell3 = {};
	cell3.className = "center";
	cell3text.label = middle;
	var cell4 = {};
	cell4.className = "center";
	cell4text.label = right;
	var cell5 = {};
	cell5.className = "center";
	cell5.id = chordNumber + "-" + inversionIndex + "-0";
	cell5.onclick = chordAction;
	cell5text.label = "Add";
	row.push(cell1);
	row.push(cell2);
	row.push(cell3);
	row.push(cell4);
	row.push(cell5);
	table.push(row);

	row = [];
	cell1 = {};
	cell1.className = "left";
	cell2 = {};
	cell2.className = "center";
	cell3 = {};
	cell3.className = "center";
	cell4 = {};
	cell4.className = "center";
	cell5 = {};
	cell5.className = "center";
	cell5.id = chordNumber + "-" + inversionIndex + "-1";
	cell5.onclick = chordAction;
	cell5text.label = "Play";
	cell1text.label = "";
	cell2text.label = leftNum;
	cell3text.label = midNum;
	cell4text.label = rightNum;
	
	row.push(cell1);
	row.push(cell2);
	row.push(cell3);
	row.push(cell4);
	row.push(cell5);
	table.push(row);
}

function getTableOfChordInfo(scaleNum)
{
	var scaleInfo = allChordInfo[scaleNum];
	var middleNumber = scaleNum % 2 === 0 ? 4 : 3;
	var table = [];
	// table.className = "small";

	var row = [];
	var headerCell = {};
	headerCelltext.label = scaleInfo.name;
	headerCell.className = "left";
	row.push(headerCell);
	table.push(row);

	oneChordInfo(scaleNum, 0, table);
	oneChordInfo(scaleNum, 1, table);
	oneChordInfo(scaleNum, 2, table);

	return table;
}