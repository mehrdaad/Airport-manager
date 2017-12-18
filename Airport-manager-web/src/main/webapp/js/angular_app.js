'use strict';
var airportManagerApp = angular.module('airportManagerApp', ['ngRoute', 'managerControllers']);
var managerControllers = angular.module('managerControllers', []);

/* Configures URL fragment routing  */
airportManagerApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider
            .when('/main', {
                templateUrl: 'partials/main.html',
                controller: "MainCtrl"
            })
            .when('/stewards', {
                templateUrl: 'partials//steward/stewards.html',
                controller: 'StewardsCtrl'
            })
            .when('/steward/:stewardId', {
                templateUrl: 'partials/steward/steward_detail.html',
                controller: 'StewardDetailCtrl'
            })
            .when('/newsteward', {
                templateUrl: 'partials/steward/new_steward.html',
                controller: 'NewStewardCtrl'
            })
            .otherwise({redirectTo: '/main'});
    }
]);


/*
 * alert closing functions defined in root scope to be available in every template
 */
airportManagerApp.run(function ($rootScope) {
    $rootScope.hideSuccessAlert = function () {
        $rootScope.successAlert = undefined;
    };
    $rootScope.hideWarningAlert = function () {
        $rootScope.warningAlert = undefined;
    };
    $rootScope.hideErrorAlert = function () {
        $rootScope.errorAlert = undefined;
    };
});

/* Controllers */

managerControllers.controller('MainCtrl', function () {

});


managerControllers.controller('StewardsCtrl',
    function ($scope, $rootScope, $routeParams, $http, $location) {
        $http.get('/pa165/api/stewards').then(function (response) {
            $scope.stewards = response.data._embedded.stewards;
            $scope.goToStewardDetail = function (stewardId) {
                $location.path('/steward/' + stewardId);
            }
        })
    }
);

managerControllers.controller('StewardDetailCtrl',
    function ($scope, $routeParams, $http, $location) {
        var stewardId = $routeParams.stewardId;
        $http.get('/pa165/api/stewards/' + stewardId).then(function (response) {
            var steward = response.data;
            $scope.steward = steward;
            $scope.deleteSteward = function (stewardId) {
                $http.delete('/pa165/api/stewards/' + stewardId).then(function (response) {
                        $location.path('/stewards');
                });
            }
        });
    }
);

managerControllers.controller('NewStewardCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {
        $scope.flight = {
            'departureLocationId': '',
            'arrivalLocationId': '',
            'departureTime': '',
            'arrivalTime': '',
            'airplaneId': '',
            'stewardsIds': []
        };

        $scope.create = function (flight) {
            console.log(flight);
        }
    }
);

// defines new directive (HTML attribute "convert-to-int") for conversion between string and int
// of the value of a selection list in a form
// without this, the value of the selected option is always a string, not an integer
managerControllers.directive('convertToInt', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            ngModel.$parsers.push(function (val) {
                return parseInt(val, 10);
            });
            ngModel.$formatters.push(function (val) {
                return '' + val;
            });
        }
    };
});