"use strict";
angular.module('sprintGraphApp').factory('MenuService', [ '$rootScope', '$state', 'rx', function($rootScope, $state, rx) {

	var sprintSelected = new rx.BehaviorSubject(null);
	var state = new rx.BehaviorSubject(null);

	$rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
		state.onNext(toState);
	})

	return {
		getState : function() {
			return state.asObservable()//
			.filter(function(v) {
				return v != null;
			})//
			.distinctUntilChanged();
		},
		getSelectedSprint : function() {
			return sprintSelected.asObservable()//
			.filter(function(v) {
				return v != null;
			})//
			.distinctUntilChanged();
		},
		setSelectedSprint : function(sprint) {
			sprintSelected.onNext(sprint);
		}
	}
} ]);