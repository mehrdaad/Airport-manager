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
            .when('/flights', {
                templateUrl: 'partials//flight/flights.html',
                controller: 'FlightsCtrl'
            })
            .when('/flight/:flightId', {
                templateUrl: 'partials/flight/flight_detail.html',
                controller: 'FlightDetailCtrl'
            })
            .when('/newflight', {
                templateUrl: 'partials/flight/new_flight.html',
                controller: 'NewFlightCtrl'
            })
            .when('/airplanes', {
                templateUrl: 'partials//airplane/airplanes.html',
                controller: 'AirplanesCtrl'
            })
            .when('/destination', {
                templateUrl: 'partials/destination.html',
                controller: 'DestinationCtrl'
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
managerControllers.controller('MainCtrl',
    function ($scope, $rootScope, $routeParams, $http) {
        $http.get('/pa165/api/flights/current').then(function (response) {
            $scope.currentFlights = response.data._embedded.flights;
            formatFlightsDates($scope.currentFlights);
        })
    }
);

managerControllers.controller('AirplanesCtrl',
    function ($scope, $rootScope, $routeParams, $http) {
        $http.get('/pa165/api/airplanes').then(function (response) {
            $scope.airplanes = response.data._embedded.airplanes;
        })
    }
);

/* Controllers */

/* Destination controller*/
function loadDestinations($http, $scope) {
    $http.get('/pa165/api/destination/').then(function (response) {
        $scope.destinations = response.data._embedded.destinations;
    });
}


managerControllers.controller('DestinationCtrl',
    function ($scope, $rootScope, $routeParams, $http) {
        $scope.sortType = 'country';
        $scope.sortReverse = false;
        $scope.searchQuery = '';
        loadDestinations($http, $scope);

        $scope.newField = {};
        $scope.editing = false;


        $scope.editAppKey = function (field) {
            $scope.editing = $scope.appkeys.indexOf(field);
            $scope.newField = angular.copy(field);
        };

        $scope.saveField = function (index) {
            if ($scope.editing !== false) {
                $scope.appkeys[$scope.editing] = $scope.newField;
                $scope.editing = false;
            }
        };

        $scope.cancel = function (index) {
            if ($scope.editing !== false) {
                $scope.appkeys[$scope.editing] = $scope.newField;
                $scope.editing = false;
            }
        };

        $scope.deleteDestination = function (destination) {
            console.log("deleting destination with id=" + destination.id);
            $http.delete('/pa165/api/' + destination.id).then(function (response) {
                $scope.destinations = response.data._embedded.destinations;
            }.then(
                function success(response) {
                    console.log('deleted product ' + product.id + ' on server');
                    //display confirmation alert
                    $rootScope.successAlert = 'Deleted product "' + product.name + '"';
                    //load new list of all products
                    loadAdminProducts($http, $scope);
                },
                function error(response) {
                    console.log("error when deleting product");
                    console.log(response);
                    switch (response.data.code) {
                        case 'ResourceNotFoundException':
                            $rootScope.errorAlert = 'Cannot delete non-existent product ! ';
                            break;
                        default:
                            $rootScope.errorAlert = 'Cannot delete product ! Reason given by the server: ' + response.data.message;
                            break;
                    }
                }
            ));
            loadDestinations($http, $scope);
        };

        $scope.newDestination = {
            'city': '',
            'country': ''
        };


        $scope.create = function (newDestination) {
            $http({
                method: 'POST',
                url: '/pa165/api/destination/create',
                data: newDestination
            }).then(function success(response) {
                console.log('created product');
                var createdDestination = response.data;
                //display confirmation alert
                $rootScope.successAlert = 'Destination "' + createdDestination.country + '" was created';
                //change view to list of products
                $location.path("/pa165/destination");
            }, function error(response) {
                //display error
                console.log("error when creating product");
                switch (response.data.code) {
                    case 'InvalidRequestException':
                        $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot create destination ! Reason given by the server: ' + response.data.message;
                        break;
                }
            });
        };

        $scope.update = function (destination) {
            $http({
                method: 'POST',
                url: '/pa165/api/destination/update',
                data: destination
            }).then(function success(response) {
                console.log('created product');
                var createdDestination = response.data;
                //display confirmation alert
                $rootScope.successAlert = 'Destination "' + createdDestination.country + '" was updated';
                //change view to list of products
                $location.path("/pa165/destination");
            }, function error(response) {
                //display error
                console.log("error when creating product");
                switch (response.data.code) {
                    case 'InvalidRequestException':
                        $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot update destination ! Reason given by the server: ' + response.data.message;
                        break;
                }
            });
        };


    });

managerControllers.controller('FlightsCtrl',
    function ($scope, $rootScope, $routeParams, $http, $location) {
        loadFlights($scope, $http);
        $scope.goToFlightDetail = function (flightId) {
            console.log(flightId);
            $location.path('/flight/' + flightId);
        };


        $scope.deleteFlight = function (flight) {
            $http.delete(flight._links.delete.href).then(
                function success(response) {
                    $rootScope.successAlert = 'Deleted Flight "' + flight.id + '"';
                },
                function error(response) {
                    switch (response.data.code) {
                        case 'ResourceNotFoundException':
                            $rootScope.errorAlert = 'Cannot delete non-existent product ! ';
                            break;
                        default:
                            $rootScope.errorAlert = 'Cannot delete product ! Reason given by the server: ' + response.data.message;
                            break;
                    }
                }
            )
        };

    }
);

managerControllers.controller('FlightDetailCtrl',
    function ($scope, $routeParams, $http) {
        var flightId = $routeParams.flightId;
        $http.get('/pa165/api/flights/' + flightId).then(function (response) {
            console.log(response.data);
            var flight = response.data;
            formatFlightDates(flight);
            $scope.flight = flight;
        });
    }
);

managerControllers.controller('NewFlightCtrl',
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

function loadFlights($scope, $http) {
    $http.get('/pa165/api/flights').then(function (response) {
        console.log(response.data);
        $scope.flights = response.data._embedded.flights;
        formatFlightsDates($scope.flights);
    });
}

function formatFlightsDates(flights) {
    for (var i = 0; i < flights.length; ++i) {
        formatFlightDates(flights[i]);
    }
}

function formatFlightDates(flight) {
    var rawDepartureDate = flight.departureTime;
    var rawArrivalDate = flight.arrivalTime;
    flight.departureTime = formatDate(rawDepartureDate);
    flight.arrivalTime = formatDate(rawArrivalDate);
}

function formatDate(date) {
    return moment(date).format("DD.MM.YYYY - h:mm A");
}
