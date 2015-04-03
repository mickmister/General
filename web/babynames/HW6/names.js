"use strict";
/*
Michael Kochell
This web page is used to display information on popular baby names.
It uses 4 ajax calls to get data in the following formats:
Plain text list of all names
HTML div of the meaning of the name
XML data describing the names rank per year
JSON listing actors with the name, including their film count
*/
var names = [];
var baseUrl = "http://wwwuser.csse.rose-hulman.edu/babynames/babynames.php?";

//this stuff happens first!
window.onload = function()
{
	getNames();
	$("search").onclick = handleSearchClick;
};

//reset for new search
function clearInfoAndDisplayLoading()
{
	$("loadingmeaning").show();
	$("loadinggraph").show();
	$("loadingcelebs").show();
	$("norankdata").hide();
	$("meaning").innerHTML = "";
	$("graph").innerHTML = "";
	$("celebs").innerHTML = "";
	$("norankdata").hide();
	$("resultsarea").show();
}

//abstract ajax call
function ajaxCall(urlEnd, onSuc, onFail)
{
	var url = baseUrl + urlEnd;
	new Ajax.Request(
		url,
		{
			method : "GET",
			onSuccess : onSuc,
			onFailure : onFail			
		}
	);
}

//retrieve and diplay actor info
function handleCelebs(name, gender)
{
	var urlEnd = "type=celebs&name=" + name + "&gender=" + gender;
	var succeed = function(ajax)
	{
		var actors = JSON.parse(ajax.responseText).actors;
		var unorderedList = $("celebs");
		for(var i=0; i<actors.length; i++)
		{
			//firstName lastName filmCount
			var actor = actors[i];
			var listItem = document.createElement("li");
			var itemString = actor.firstName + " " + actor.lastName + " (" 
				+ actor.filmCount + " films)";
			listItem.innerHTML = itemString;
			unorderedList.appendChild(listItem);
		}
		$("loadingcelebs").hide();
	};
	var fail = function(ajax)
	{
		handleError(ajax, baseUrl + urlEnd);
	};
	ajaxCall(urlEnd, succeed, fail);
}


//retrieve and display name ranking info
function handleRankings(name, gender)
{
	var endUrl = "type=rank&name=" + name + "&gender=" + gender;
	var success = function(ajax)
	{
		var xml = ajax.responseXML;
		var baby = xml.getElementsByTagName("baby")[0];
		var ranks = baby.getElementsByTagName("rank");
		populateRankGraph(ranks);
	};
	var fail = function(ajax)
	{
		if(ajax.status == 410)
		{
			$("norankdata").show();
			$("loadinggraph").hide();
		}
		else
		{
			handleError(ajax, baseUrl + urlEnd);
		}
	};
	ajaxCall(endUrl, success, fail);
}

//take rank XML and populate graph
function populateRankGraph(ranks)
{
	var table = $("graph");
	var yearRow = document.createElement("tr");
	var barRow = document.createElement("tr");
	var year = parseInt(ranks[0].getAttribute("year"));
	for(var i = 0; i < ranks.length; i++)
	{
		while(parseInt(ranks[i].getAttribute("year")) - year > 10)
		{
			year += 10;
			var yearHeader = document.createElement("th");
			yearHeader.innerHTML = year + "";
			yearRow.appendChild(yearHeader);
			var rankCell = getRankingCell(0);
			barRow.appendChild(rankCell);	
		}
		year = parseInt(ranks[i].getAttribute("year"));;
		var yearHeader = document.createElement("th");
		yearHeader.innerHTML = year + "";
		yearRow.appendChild(yearHeader);

		var rankCell = getRankingCell(ranks[i].firstChild.nodeValue);
		barRow.appendChild(rankCell);			
	}
	$("loadinggraph").hide();
	table.appendChild(yearRow);
	table.appendChild(barRow);
}

//creates a td rank cell for the gicven rankNumber
function getRankingCell(rankNumber)
{
	var cell = document.createElement("td");
	var bar = document.createElement("div");
	bar.addClassName("bar");
	bar.innerHTML = rankNumber;
	if(rankNumber == 0)
	{
		bar.style.height = "0px";
		cell.appendChild(bar);
		return cell;
	}
	else if(rankNumber <= 10)
	{
		bar.addClassName("popular");
	}
	else
	{
		bar.addClassName("unpopular");
	}

	var height = parseInt(1/4 * (1000 - rankNumber));
	var heightPixels = height + "px";
	bar.style.height = heightPixels;
	cell.appendChild(bar);
	return cell;
}

//retrieves and displays the meaning of the name
function handleMeaning(name)
{
	var urlEnd = "type=meaning&name=" + name;
	var succeed = function(ajax)
	{
		$("loadingmeaning").hide();
		var text = ajax.responseText;
		$("meaning").innerHTML = text;
	};
	var fail = function(ajax)
	{
		handleError(ajax, baseUrl + urlEnd);
	};
	ajaxCall(urlEnd, succeed, fail);
}

//retrieves and displays names in the dropdown list
function getNames()
{
	var urlEnd = "type=list";
	var succeed = function(ajax)
	{
		$("loadingnames").hide();
		handleNames(ajax);
	};
	var fail = function(ajax)
	{
		handleError(ajax, baseUrl + urlEnd);
	};
	ajaxCall(urlEnd, succeed, fail);
}

//delegate all search functions
function searchForName(name)
{
	clearInfoAndDisplayLoading();
	handleMeaning(name);
	var gender = "f";
	if($("genderm").checked == true)
	{
		gender = "m";
	}
	handleRankings(name, gender);
	handleCelebs(name, gender);
}

//click function for search button, calls searchForName
function handleSearchClick()
{
	var select = $("allnames");
	var nameSearch = select.options[select.selectedIndex].text;
	if(nameSearch == "(choose a name)")
	{
		$("resultsarea").hide();
	}
	else
	{
		searchForName(nameSearch);
	}
}

//populates select dropdown with name options
function handleNames(ajax)
{
	var text = ajax.responseText;
	names = text.split("\n");
	names.sort();
	var select = $("allnames");
	for(var i=0; i<names.length; i++)
	{
		var option = document.createElement("option");
		option.innerHTML = names[i];
		select.appendChild(option);
	}
	select.disabled = false;
}

//handle error if not 410 from ranking
function handleError(ajax, url)
{
	var errorString = "Error fetching ajax data " + url + "<br />"
		+ ajax.status + " " + ajax.statusText;
	$("errors").innerHTML = errorString;
	$("loadingmeaning").hide();
	$("loadinggraph").hide();
	$("loadingcelebs").hide();
}