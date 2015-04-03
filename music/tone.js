
var frequencies = [];
frequencies[0] = 261.63;
frequencies[1] = 277.18;
frequencies[2] = 293.66;
frequencies[3] = 311.13;
frequencies[4] = 329.63;
frequencies[5] = 349.23;
frequencies[6] = 369.99;
frequencies[7] = 392;
frequencies[8] = 415.3;
frequencies[9] = 440;
frequencies[10] = 466.16;
frequencies[11]	= 493.88;
frequencies[12]	= 523.25;
frequencies[13]	= 554.37;
frequencies[14]	= 587.33;
frequencies[15]	= 622.25;
frequencies[16]	= 659.25;
frequencies[17]	= 698.46;
frequencies[18]	= 739.99;
frequencies[19]	= 783.99;
frequencies[20]	= 830.61;
frequencies[21]	= 880;
frequencies[22]	= 932.33;
frequencies[23]	= 987.77;

var isChrome = /Chrome/.test(navigator.userAgent) && /Google Inc/.test(navigator.vendor);
var isSafari = /Safari/.test(navigator.userAgent) && /Apple Computer/.test(navigator.vendor);
if(isChrome || isSafari){
	var context = new webkitAudioContext();
}else{
	var context = null;
}

function noteHit(noteNumber, key){
	if(context){
		var oscillator = context.createOscillator();
		oscillator.type = 0;
		oscillator.frequency.value = frequencies[noteNumber];
		oscillator.connect(context.destination);
		key.classList.add("red");
		play(oscillator);
		setTimeout(function() {stop(oscillator); key.classList.remove("red"); }, 250);
	}
}

function play(oscillator){
	oscillator.noteOn && oscillator.noteOn(0);
};

function stop(oscillator){
	oscillator.noteOff && oscillator.noteOff(0);
};
