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
            .when('/login', {
                templateUrl: 'partials/login.html',
                controller: 'LoginCtrl'
            })
            .when('/stewards', {
                templateUrl: 'partials//steward/stewards.html',
                controller: 'StewardsCtrl'
            })
            .when('/steward/:stewardId', {
                templateUrl: 'partials/steward/steward_detail.html',
                controller: 'StewardDetailCtrl'
            })
            .when('/flights', {
                templateUrl: 'partials//flight/flights.html',
                controller: 'FlightsCtrl'
            })
            .when('/flight/:flightId', {
                templateUrl: 'partials/flight/flight_detail.html',
                controller: 'FlightDetailCtrl'
            })
            .when('/airplanes', {
                templateUrl: 'partials//airplane/airplanes.html',
                controller: 'AirplanesCtrl'
            })
            .when('/airplane/:airplaneId', {
                templateUrl: 'partials/airplane/airplane_detail.html',
                controller: 'AirplaneDetailCtrl'
            })
            .when('/destinations', {
                templateUrl: 'partials/destination/destination.html',
                controller: 'DestinationCtrl'
            })
            .when('/destinations/:destinationId', {
                templateUrl: 'partials/destination/destination_detail.html',
                controller: 'DestinationDetailCtrl'
            })
            .otherwise({redirectTo: '/main'});
    }
]);

airportManagerApp.constant('USER_ROLES', {
    admin: 'admin',
    user: 'user',
    guest: 'guest'
});


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

managerControllers.controller('ApplicationController',
    function ($scope, USER_ROLES, AuthService) {
        $scope.currentUser = null;
        $scope.userRoles = USER_ROLES;
        $scope.isAuthorized = AuthService.isAuthorized;

        $scope.setCurrentUser = function (user) {
            $scope.currentUser = user;
        };

        $scope.logout = function () {
            $scope.currentUser = null;
            AuthService.logout();
        };
    }
);

managerControllers.controller('MainCtrl',
    function ($scope, $rootScope, $routeParams, $http) {
        $http.get('/pa165/api/flights/current').then(function (response) {
            $scope.currentFlights = response.data._embedded.flights;
            formatFlightsDates($scope.currentFlights);
        })
    }
);


managerControllers.controller('LoginCtrl',
    function ($scope, $rootScope, $routeParams, $http, $location, AuthService) {
        $scope.credentials = {
            email: '',
            password: ''
        };

        $scope.fail = false;

        $scope.login = function (credentials) {
            AuthService.login(credentials).then(function (user) {
                $scope.setCurrentUser(user);
                $location.path('/main');
            }, function error(reason) {
                $scope.fail = true;
            });
        };
    });


managerControllers.controller('AirplanesCtrl',
    function ($scope, $rootScope, $routeParams, $http, $location) {
        var get = function () {
            $http.get('/pa165/api/airplanes').then(function (response) {
                $scope.airplanes = response.data._embedded.airplanes;
                $scope.goToAirplaneDetail = function (airplaneId) {
                    console.log(airplaneId);
                    $location.path('/airplane/' + airplaneId);
                }
            });
        };
        get();
        $scope.airplane = {
            'name': '',
            'type': '',
            'capacity': ''
        };

        $scope.createAirplane = function (airplane) {
            console.log(airplane);
            $http({
                method: 'POST',
                url: '/pa165/api/airplanes/create',
                data: airplane
            }).then(function (response) {
                console.log(response);
                get();
            });
        }

    }
);

managerControllers.controller('AirplaneDetailCtrl',
    function ($scope, $routeParams, $http) {
        var airplaneId = $routeParams.airplaneId;
        $http.get('/pa165/api/airplanes/' + airplaneId).then(function (response) {
            console.log(response.data);
            var airplane = response.data;
            $scope.airplane = airplane;
        });
    }
);

/* Controllers */
managerControllers.controller('DestinationDetailCtrl',
    function ($scope, $routeParams, $http, $rootScope) {
        var tempDestination = {
            'id': "",
            'country': "",
            'city': ""
        };

        var destinationId = $routeParams.destinationId;
        $http.get('/pa165/api/destinations/' + destinationId).then(function (response) {
            console.log(response.data);
            //formatFlightDates(flight);
            $scope.destination = response.data;
        });

        $http.get('/pa165/api/destinations/' + destinationId + "/incomingFlights").then(function (response) {
            console.log(response.data);
            $scope.incomingFlights = response.data._embedded.flights;
        });

        $http.get('/pa165/api/destinations/' + destinationId + "/outgoingFlights").then(function (response) {
            console.log(response.data);
            $scope.outgoingFlights = response.data._embedded.flights;
        });

        $scope.saveTempDestination = function (destination) {
            tempDestination.country = destination.country;
            tempDestination.city = destination.city;
        };

        $scope.loadTempDestination = function () {
            $scope.destination.country = tempDestination.country;
            $scope.destination.city = tempDestination.city;
        };

        $scope.updateDestination = function (destination) {
            console.log(destination);
            var data = {
                'id': destination.id,
                'country': destination.country,
                'city': destination.city
            };
            $http({
                method: 'POST',
                url: '/pa165/api/destinations/' + destination.id + '/update',
                data: data
            }).then(function success(response) {
                console.log(response);
                $rootScope.successAlert = 'Destination was successfully updated.';
            }, function error(response) {
                console.log(response);
                $rootScope.errorAlert = 'Error during updating steward.';
            });
        }
    }
);
managerControllers.controller('DestinationCtrl',
    function ($scope, $rootScope, $routeParams, $http, $location) {
        $scope.sortType = 'country';
        $scope.sortReverse = false;
        $scope.searchQuery = '';


        var get = function () {
            $http.get('/pa165/api/destinations').then(function (response) {
                if (response.data._embedded !== undefined) {
                    $scope.destinations = response.data._embedded.destinations;
                } else {
                    $scope.destinations = [];
                }
                $scope.goToDestinationDetail = function (destinationId) {
                    $location.path('/destinations/' + destinationId);
                }
            });
        };
        get();

        $scope.createDestination = function (destination) {
            console.log(destination);
            $http({
                method: 'POST',
                url: '/pa165/api/destinations/create',
                data: destination
            }).then(function (response) {
                console.log(response);
                get();
            });

        };


        $scope.deleteDestination = function (destination) {
            $http.delete('/pa165/api/destinations/' + destination).then(function success(response) {
                $rootScope.successAlert = 'Destination was successfully deleted.';
                get();
            }, function error(response) {
                console.log("Error during deleting destination!");
                console.log(steward);
                $rootScope.errorAlert = 'Destination has assigned flights. Cannot be deleted.';
                switch (response.data.code) {
                    case 'PersistenceException':
                        $rootScope.errorAlert = 'Destination has assigned flights. Cannot be deleted.';
                        break;
                    case 'JpaSystemException':
                        $rootScope.errorAlert = 'Destination has assigned flights. Cannot be deleted.';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot delete destination! Reason given by the server: ' + response.data.message;
                        break;
                }
            });
        };
    }
);

managerControllers.controller('StewardsCtrl',
    function ($scope, $rootScope, $routeParams, $http, $location) {
        var get = function () {
            $http.get('/pa165/api/stewards').then(function (response) {
                if (response.data._embedded !== undefined) {
                    $scope.stewards = response.data._embedded.stewards;
                } else {
                    $scope.stewards = [];
                }

                $scope.goToStewardDetail = function (stewardId) {
                    $location.path('/steward/' + stewardId);
                }
            });
        };
        get();
        $scope.steward = {
            'firstName': '',
            'surname': ''
        };
        $scope.createSteward = function (steward) {
            console.log(steward);
            $http({
                method: 'POST',
                url: '/pa165/api/stewards/create',
                data: steward
            }).then(function success(response) {
                console.log(response);
                get();
                $rootScope.successAlert = 'Steward was successfully created.';
            }, function error(response) {
                console.log(response);
                $rootScope.errorAlert = 'Problem during creating steward.';
            });
        };
        $scope.deleteSteward = function (steward) {
            $http.delete('/pa165/api/stewards/' + steward).then(function success(response) {
                $rootScope.successAlert = 'Steward was successfully deleted.';
                get();
            }, function error(response) {
                console.log("Error during deleting steward!");
                console.log(steward);
                switch (response.data.code) {
                    case 'PersistenceException':
                        $rootScope.errorAlert = 'Steward has assigned flights. Cannot be deleted.';
                        break;
                    case 'JpaSystemException':
                        $rootScope.errorAlert = 'Steward has assigned flights. Cannot be deleted.';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot delete steward! Reason given by the server: ' + response.data.message;
                        break;
                }
            });
        }
    }
);

managerControllers.controller('StewardDetailCtrl',
    function ($scope, $routeParams, $http, $rootScope) {
        var stewardId = $routeParams.stewardId;
        $http.get('/pa165/api/stewards/' + stewardId).then(function (response) {
            $scope.steward = response.data;
            $scope.stewardConst = angular.copy($scope.steward);
        });
        $http.get('/pa165/api/stewards/' + stewardId + '/flights').then(function (response) {
            console.log(response);
            var flights = response.data._embedded.flights;
            $scope.flights = flights;
            formatFlightsDates($scope.flights);
        });
        $scope.updateSteward = function (steward) {
            console.log(steward);
            var stewardData = {
                'id': steward.id,
                'firstName': steward.firstName,
                'surname': steward.surname
            };
            $http({
                method: 'POST',
                url: '/pa165/api/stewards/' + steward.id + '/update/',
                data: stewardData
            }).then(function success(response) {
                console.log(response);
                $rootScope.successAlert = 'Steward was successfully updated.';
                $scope.stewardConst = angular.copy($scope.steward);
            }, function error(response) {
                console.log(response);
                $rootScope.errorAlert = 'Error during updating steward.';
            });
        }
    }
);

managerControllers.controller('FlightsCtrl',
    function ($scope, $rootScope, $routeParams, $http, $location) {
        loadFlights($scope, $http);

        $scope.createFlightModel = function () {
            $scope.flight = {
                'departureLocationId': '',
                'arrivalLocationId': '',
                'departureTime': undefined,
                'arrivalTime': undefined,
                'airplaneId': '',
                'stewardIds': []
            };
        };

        $scope.createFlightModel();

        $scope.goToFlightDetail = function (flightId) {
            $location.path('/flight/' + flightId);
        };

        $http.get('/pa165/api/destinations').then(function (response) {
            $scope.destinations = response.data._embedded.destinations;
        });

        /** DateTimePicker */

        $scope.areDatesSet = false;

        $scope.optionsDepartureTime = {
            useCurrent: true,
            format: "DD.MM.YYYY - h:mm A",
            showClear: true,
            showClose: true,
            toolbarPlacement: 'top'
        };

        $scope.optionsArrivalTime = {
            useCurrent: false,
            format: "DD.MM.YYYY - h:mm A",
            showClear: true,
            showClose: true,
            toolbarPlacement: 'top'
        };

        $scope.$watchGroup(['flight.departureTime', 'flight.arrivalTime'], function (newVal, oldVal) {
            if (newVal[0] !== undefined && newVal[1] !== undefined) {
                $http({
                    url: '/pa165/api/stewards/free',
                    method: 'GET',
                    params: {
                        start: moment(newVal[0]).subtract(1, "hours").format("YYYY-MM-DDTHH:mm:ss"),
                        end: moment(newVal[1]).subtract(1, "hours").format("YYYY-MM-DDTHH:mm:ss")
                    }
                }).then(function (response) {
                    $scope.stewards = response.data._embedded.stewards;
                });

                $http({
                    url: '/pa165/api/airplanes/free',
                    method: 'GET',
                    params: {
                        start: moment(newVal[0]).subtract(1, "hours").format("YYYY-MM-DDTHH:mm:ss"),
                        end: moment(newVal[1]).subtract(1, "hours").format("YYYY-MM-DDTHH:mm:ss")
                    }
                }).then(function (response) {
                    $scope.airplanes = response.data._embedded.airplanes;
                });

                $scope.areDatesSet = true;
            } else {
                $scope.areDatesSet = false;
                $scope.stewards = [];
                $scope.airplanes = [];
            }
        });

        /** DateTimePicker - end */

        $scope.createFlight = function (flight) {
            console.log(flight);

            $http({
                method: 'POST',
                url: '/pa165/api/flights/create',
                data: flight
            }).then(
                function success(response) {
                    var createdFlight = response.data;
                    $rootScope.successAlert = 'A new flight "' + createdFlight.id + '" was created';
                    loadFlights($scope, $http);
                },
                function error(response) {
                    console.log(response);
                    switch (response.data.code) {
                        case 'InvalidRequestException':
                            $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                            break;
                        default:
                            $rootScope.errorAlert = 'Cannot create flight ! Reason given by the server: ' + response.data.message + response.data.code;
                            break;
                    }
                });
        };


        $scope.deleteFlight = function (flight) {
            $http.delete(flight._links.delete.href).then(
                function success(response) {
                    $rootScope.successAlert = 'Deleted Flight "' + flight.id + '"';
                    loadFlights($scope, $http);
                },
                function error(response) {
                    switch (response.data.code) {
                        case 'ResourceNotFoundException':
                            $rootScope.errorAlert = 'Cannot delete non-existent flight ! ';
                            break;
                        default:
                            $rootScope.errorAlert = 'Cannot delete flight ! Reason given by the server: ' + response.data.message;
                            break;
                    }
                }
            )
        };

    }
);

managerControllers.controller('FlightDetailCtrl',
    function ($scope, $routeParams, $http, $rootScope, $route, $timeout) {
        var flightId = $routeParams.flightId;
        $http.get('/pa165/api/flights/' + flightId).then(function (response) {
            var flight = response.data;
            $scope.flightToUpdate = {
                'id': flight.id,
                'departureLocationId': flight.departureLocation.id,
                'arrivalLocationId': flight.arrivalLocation.id,
                'departureTime': flight.departureTime,
                'arrivalTime': flight.arrivalTime,
                'airplaneId': flight.airplane.id,
                'stewardIds': []
            };

            flight.stewards.forEach(function (value) {
                $scope.flightToUpdate.stewardIds.push(value.id);
            });

            $http.get('/pa165/api/destinations').then(function (response) {
                $scope.destinations = response.data._embedded.destinations;
            });

            /** DateTimePicker */

            $scope.areDatesSet = false;

            $scope.optionsDepartureTime = {
                defaultDate: moment(flight.departureTime),
                maxDate: moment(flight.arrivalTime),
                showClear: true,
                showClose: true,
                format: "DD.MM.YYYY - h:mm A",
                toolbarPlacement: 'top'
            };

            $scope.optionsArrivalTime = {
                defaultDate: moment(flight.arrivalTime),
                minDate: moment(flight.departureTime),
                showClear: true,
                showClose: true,
                format: "DD.MM.YYYY - h:mm A",
                toolbarPlacement: 'top'
            };

            formatFlightDates(flight);
            $scope.flight = flight;

            $scope.$watchGroup(['flightToUpdate.departureTime', 'flightToUpdate.arrivalTime'], function (newVal, oldVal) {
                if (newVal[0] !== undefined && newVal[1] !== undefined) {
                    $http({
                        url: '/pa165/api/stewards/free',
                        method: 'GET',
                        params: {
                            start: moment(newVal[0]).subtract(1, "hours").format("YYYY-MM-DDTHH:mm:ss"),
                            end: moment(newVal[1]).subtract(1, "hours").format("YYYY-MM-DDTHH:mm:ss")
                        }
                    }).then(function (response) {
                        $scope.stewards = response.data._embedded.stewards;
                        $scope.flight.stewards.forEach(function (value) {
                            var check = function (steward) {
                                return steward.id === value.id;
                            };
                            if ($scope.stewards.find(check) === undefined) {
                                $scope.stewards.unshift(value);
                            }
                        });
                    });

                    $http({
                        url: '/pa165/api/airplanes/free',
                        method: 'GET',
                        params: {
                            start: moment(newVal[0]).subtract(1, "hours").format("YYYY-MM-DDTHH:mm:ss"),
                            end: moment(newVal[1]).subtract(1, "hours").format("YYYY-MM-DDTHH:mm:ss")
                        }
                    }).then(function (response) {
                        $scope.airplanes = response.data._embedded.airplanes;
                        var check = function (airplane) {
                            return airplane.id === $scope.flight.airplane.id;
                        };
                        if ($scope.airplanes.find(check) === undefined) {
                            $scope.airplanes.unshift($scope.flight.airplane);
                        }
                    });
                    $scope.areDatesSet = true;
                } else {
                    $scope.areDatesSet = false;
                    $scope.stewards = [];
                    $scope.airplanes = [];
                }
            });

            /** DateTimePicker - end */

        });

        $scope.updateFlight = function (flight) {
            $http({
                method: 'POST',
                url: '/pa165/api/flights/' + flight.id + '/update/',
                data: flight
            }).then(function success(response) {
                $rootScope.successAlert = 'Flight was successfully updated.';
                $timeout(function () {
                    $route.reload();
                }, 1000);

            }, function error(response) {
                console.log(response);
                $rootScope.errorAlert = 'Error during updating flight.';
            });
        }
    }
);

function loadFlights($scope, $http) {
    $http.get('/pa165/api/flights').then(function (response) {
        if (response.data._embedded !== undefined) {
            $scope.flights = response.data._embedded.flights;
            formatFlightsDates($scope.flights);
        } else {
            $scope.flights = [];
        }

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

// defines new directive (HTML attribute "convert-to-int") for conversion between string and int
// of the value of a selection list in a form
// without this, the value of the selected option is always a string, not an integer
managerControllers.directive('convertToInt', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            ngModel.$parsers.push(function (val) {
                if (val === undefined) return;
                return parseInt(val, 10);
            });
            ngModel.$formatters.push(function (val) {
                if (val === undefined) return;
                return '' + val;
            });
        }
    };
});

managerControllers.directive('convertToInts', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            ngModel.$parsers.push(function (val) {
                if (val === undefined) return;
                var parsed = [];
                for (var i = 0; i < val.length; ++i) {
                    parsed.push(parseInt(val[i], 10))
                }
                return parsed;
            });
            ngModel.$formatters.push(function (val) {
                if (val === undefined) return;
                var formatted = [];
                for (var i = 0; i < val.length; ++i) {
                    formatted.push('' + val[i])
                }
                return formatted;
            });
        }
    };
});


airportManagerApp.directive('datetimepicker', [
    '$timeout',
    function ($timeout) {
        return {
            restrict: 'EA',
            require: 'ngModel',
            scope: {
                options: '=?',
                onChange: '&?',
                onClick: '&?'
            },
            link: function ($scope, $element, $attrs, ngModel) {
                var dpElement = $element.parent().hasClass('input-group') ? $element.parent() : $element;

                $scope.$watch('options', function (newValue) {
                    var dtp = dpElement.data('DateTimePicker');
                    $.map(newValue, function (value, key) {
                        dtp[key](value);
                    });
                }, true);

                ngModel.$render = function () {
                    // if value is undefined/null do not do anything, unless some date was set before
                    var currentDate = dpElement.data('DateTimePicker').date();
                    if (!ngModel.$viewValue && currentDate) {
                        dpElement.data('DateTimePicker').clear();
                    } else if (ngModel.$viewValue) {
                        // otherwise make sure it is moment object
                        if (!moment.isMoment(ngModel.$viewValue)) {
                            ngModel.$setViewValue(moment(ngModel.$viewValue));
                        }
                        dpElement.data('DateTimePicker').date(ngModel.$viewValue);
                    }
                };

                var isDateEqual = function (d1, d2) {
                    return moment.isMoment(d1) && moment.isMoment(d2) && d1.valueOf() === d2.valueOf();
                };

                dpElement.on('dp.change', function (e) {
                    if (!isDateEqual(e.date, ngModel.$viewValue)) {
                        var newValue = e.date === false ? null : moment(e.date).add(1, "hours");
                        ngModel.$setViewValue(newValue !== null ? newValue._d : null);

                        $timeout(function () {
                            if (typeof $scope.onChange === 'function') {
                                $scope.onChange();
                            }
                        });
                    }
                });


                dpElement.on('click', function () {
                    $timeout(function () {
                        if (typeof $scope.onClick === 'function') {
                            $scope.onClick();
                        }
                    });
                });

                dpElement.datetimepicker($scope.options);
            }
        };
    }
]);

airportManagerApp.factory('AuthService', function ($http, Session, USER_ROLES) {
    var authService = {};

    authService.login = function (credentials) {
        console.log(credentials);
        return $http({
            method: 'POST',
            url: '/pa165/api/user',
            data: credentials
        }).then(function (res) {
            console.log("here");
            console.log(res);
            if (res.data !== undefined) {
                var user = res.data;
                console.log(user);
                var role = user.admin ? USER_ROLES.admin : USER_ROLES.user;
                Session.create(user.id, user.name, user.surname, role);
                return user;
            }

        });
    };

    authService.isAuthenticated = function () {
        return !!Session.userId;
    };

    authService.isAuthorized = function (authorizedRoles) {
        if (!angular.isArray(authorizedRoles)) {
            authorizedRoles = [authorizedRoles];
        }
        return (authService.isAuthenticated() &&
            authorizedRoles.indexOf(Session.userRole) !== -1);
    };

    authService.logout = function () {
        Session.destroy();
    };

    return authService;
});

airportManagerApp.service('Session', function () {
    this.create = function (userId, username, userSurname, userRole) {
        this.userId = userId;
        this.username = username;
        this.userSurname = userSurname;
        this.userRole = userRole;
    };

    this.destroy = function () {
        this.userId = null;
        this.username = null;
        this.userSurname = null;
        this.userRole = null;
    };
});