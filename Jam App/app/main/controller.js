app.controller('MainController', ['ScaleService', '$scope', function(ScaleService, $scope)
{
	//C MAJOR
	var default_scale = 0;
	$scope.scale = [];	

	$scope.scaleOptions = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23];

	$scope.choice = $scope.scaleOptions[0];

	$scope.scaleIndexes = [0, 1, 2, 3, 4, 5, 6];

	$scope.progression = [];

	ScaleService.getScaleInfo(default_scale, function(scale)
	{
		$scope.scale = scale;
	});

	$scope.update = function()
	{
		ScaleService.getScaleInfo($scope.choice, function(scale)
		{
			$scope.scale = scale;
		});
	};

	$scope.addChord = function(chord)
	{
		$scope.progression.push(angular.copy(chord));
	};

	$scope.incrementChoice = function()
	{
		if($scope.choice < 23)
		{
			$scope.choice = $scope.scaleOptions[$scope.choice + 1];
		}
	};

	$scope.decrementChoice = function()
	{
		if($scope.choice > 0)
		{
			$scope.choice = $scope.scaleOptions[$scope.choice - 1];
		}
	};

	$scope.clearProgression = function()
	{
		$scope.progression = [];
	};

}]);

app.controller('LandingController', function()
{

});



app.directive('chordDir', function()
{
    return {
        restrict: 'E',
        templateUrl: 'app/main/scale-view.html',
        
        scope: '= chord',
        link: function($scope, $elements, $attributes)
        {
        	
        }
    };
});

app.directive('piano', function()
{
	return {
		restrict: 'E',
		templateUrl: 'app/main/piano.html',

		scope: {
			'chord': '=',
			'pianoKeys': '='
		},
		link: function($scope, $elements, $attributes)
        {
        	var chord = $scope.chord;
        	var chordIndices = [
        		chord.rootindex,
        		chord.rootindex + 12,
        		chord.thirdindex,
        		chord.thirdindex + 12,
        		chord.fifthindex,
        		chord.fifthindex + 12,

        	];

        	$scope.getKeySpanClass = function(key)
        	{
        		if(key.color === 'white')
        		{
        			return 'whitekeyspan';
        		}
        		else
        		{
        			return 'blackkeyspan';
        		}
        	};

        	$scope.getKeySpanText = function(key)
        	{
        		if(key.index % 12 === chord.rootindex)
        		{
        			return 'root';
        		}
        		else
        		{
        			return '|';
        		}
        	};

        	$scope.getKeyColor = function(key)
        	{
        		var classes = [key.color];
        		if(chordIndices.indexOf(key.index) > -1)
        		{

        			if(key.index % 12 === chord.rootindex)
        			{
        				classes.push('red');
        			}
        			else
        			{
        				if(key.index >= 12 || key.index % 12 > chord.rootindex)
        				{
        					classes.push('blue');
        				}
        				
        			}
        		}
        		return classes;

        	};

        	$scope.pianoKeys = [
				{
					color: 'white',
					note: 'C',
					index: 0
				},
				{
					color: 'black',
					note: 'C#',
					index: 1
				},
				{
					color: 'white',
					note: 'D',
					index: 2
				},
				{
					color: 'black',
					note: 'Eb',
					index: 3
				},
				{
					color: 'white',
					note: 'E',
					index: 4
				},
				{
					color: 'white',
					note: 'F',
					index: 5
				},
				{
					color: 'black',
					note: 'F#',
					index: 6
				},
				{
					color: 'white',
					note: 'G',
					index: 7
				},
				{
					color: 'black',
					note: 'G#',
					index: 8
				},
				{
					color: 'white',
					note: 'A',
					index: 9
				},
				{
					color: 'black',
					note: 'Bb',
					index: 10
				},
				{
					color: 'white',
					note: 'B',
					index: 11
				},
				{
					color: 'white',
					note: 'C',
					index: 12
				},
				{
					color: 'black',
					note: 'C#',
					index: 13
				},
				{
					color: 'white',
					note: 'D',
					index: 14
				},
				{
					color: 'black',
					note: 'Eb',
					index: 15
				},
				{
					color: 'white',
					note: 'E',
					index: 16
				},
				{
					color: 'white',
					note: 'F',
					index: 17
				},
				{
					color: 'black',
					note: 'F#',
					index: 18
				},
				{
					color: 'white',
					note: 'G',
					index: 19
				},
				{
					color: 'black',
					note: 'G#',
					index: 20
				},
				{
					color: 'white',
					note: 'A',
					index: 21
				},
				{
					color: 'black',
					note: 'Bb',
					index: 22
				},
				{
					color: 'white',
					note: 'B',
					index: 23
				}
			];
        	
        }

	};
});


