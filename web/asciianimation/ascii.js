// CONSTANTS
var SPEEDS = [];
SPEEDS["Turbo"] = SPEEDS["turbo"] = SPEEDS["TURBO"] = 50;
SPEEDS["Normal"] = SPEEDS["normal"] = SPEEDS["NORMAL"] = 250;
SPEEDS["Slo-Mo"] = SPEEDS["slo-mo"] = SPEEDS["SLO-MO"] = 1000;
var DEFAULTSPEED = SPEEDS["normal"];
var DEFAULTFONTSIZE = "12pt"


//GLOBAL VARIABLES
var animationArray;
var totalFrames;
var currentFrame = 0;
var currentSpeed = DEFAULTSPEED;
var animationInterval;
var fullAnimation;


//run initial setup
window.onload = function()
{
	ANIMATIONS["Custom"] = ANIMATIONS["custom"] = ANIMATIONS["CUSTOM"] = CUSTOM_ANIMATION;
	buttonSetup();
	dropDownSetup();
	radioButtonSetup();
	var textArea = document.getElementById("animation_space");
	textArea.value = ANIMATIONS["blank"];
	textArea.style.fontSize = DEFAULTFONTSIZE;
};

//assign onclick functions to start and stop buttons
function buttonSetup()
{
	var stopButton = document.getElementById("stop");
	stopButton.disabled = true;
	stopButton.onclick = stopButtonEngaged;
	var startButton = document.getElementById("start");
	startButton.onclick = startButtonEngaged;	
};

//assign onclick functions to drop down "select" lists
function dropDownSetup()
{
	var animationChoiceMenu = document.getElementsByName("animation_choice")[0];
	animationChoiceMenu.onchange = animationSwitch;
	var sizeChoiceMenu = document.getElementsByName("size_choice")[0];
	sizeChoiceMenu.onchange = setFontSize;
};

//assign onclick functions to radio buttons
function radioButtonSetup()
{
	var radioButtons = document.getElementsByName("speed");
	for(var i=0; i < radioButtons.length; i++)
	{
		radioButtons[i].onclick = changeSpeed;
	}
	
}

//set current state of textarea
function setTextAreaLoop()
{
	var textArea = document.getElementById("animation_space");
	textArea.value = animationArray[currentFrame];
	currentFrame = (currentFrame + 1) % totalFrames;
};

//start button has been pressed
function startButtonEngaged()
{
	var startButton = document.getElementById("start");
	startButton.disabled = true;
	var stopButton = document.getElementById("stop");
	stopButton.disabled = false;
	var animationChoiceMenu = document.getElementsByName("animation_choice")[0];
	animationChoiceMenu.disabled = true;
	var textArea = document.getElementById("animation_space");
	var stringInTextArea =  textArea.value;
	fullAnimation = stringInTextArea;
	animationArray = stringInTextArea.split("=====\n");
	totalFrames = animationArray.length;
	currentFrame = 0;
	animationInterval = window.setInterval(function() {setTextAreaLoop()}, currentSpeed);
};

//stop button has been pressed
function stopButtonEngaged()
{
	var stopButton = document.getElementById("stop");
	stopButton.disabled = true;
	var startButton = document.getElementById("start");
	startButton.disabled = false;
	var animationChoiceMenu = document.getElementsByName("animation_choice")[0];
	animationChoiceMenu.disabled = false;
	window.clearInterval(animationInterval);
	var textArea = document.getElementById("animation_space");
	textArea.value = fullAnimation;
};

//animation choice has swtiched
function animationSwitch()
{
	stopButtonEngaged();
	var animationChoice = this.value;
	var textArea = document.getElementById("animation_space");
	textArea.value = ANIMATIONS[animationChoice];
};

//font size choice has swtiched
function setFontSize()
{
	var textArea = document.getElementById("animation_space");
	textArea.style.fontSize = this.value + "pt";
};


//loop speed choice has swtiched
function changeSpeed()
{
	currentSpeed = SPEEDS[this.value];
	var stopButton = document.getElementById("stop");
	if(stopButton.disabled == false)
	{
		window.clearInterval(animationInterval);
		animationInterval = window.setInterval(function() {setTextAreaLoop()}, currentSpeed);
	}
};

