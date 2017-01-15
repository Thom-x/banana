"use strict";
angular.module('sprintGraphApp').factory('MemberService', [ 'MemberResource', 'AbsenceService', 'rx', function(memberResource, absenceService, rx) {

	function daysInMonth(month, year) {
		return new Date(year, month, 0).getDate();
	}

	function isPresent(date, absences){
		return absences.filter(function(absence){
			return (absence.start != null && absence.end != null)
		}).filter(function(absence){
			var startDate = new Date(absence.start);
			startDate =	new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate());
			return (startDate.getTime() <= date.getTime());
		}).filter(function(absence){
			return ( new Date(absence.end).getTime() >= date.getTime());
		})
		.reduce(function(acc, absence){
			return false;
		},true)
	}
	function	getAbsences(member) {
		return rx.Observable.just(member).map(function(s) {
			return s.id;
		}).flatMap(function(id) {
			return rx.Observable.fromPromise(memberResource.getAbsences({
				id : id
			}).$promise)
		}).map(function(result) {
			return result._embedded.absences;
		});
	}

	return {
		get : function(id) {
			return rx.Observable.fromPromise(memberResource.get({
				id : id
			}).$promise);
		},
		save : function(member) {
			return rx.Observable.fromPromise(memberResource.save(member).$promise);
		},
		getAll : function() {
			return rx.Observable.fromPromise(memberResource.get().$promise).map(function(result) {
				return result._embedded.members;
			});
		},
		update : function(member) {
			return rx.Observable.fromPromise(memberResource.update({
				id : member.id,
			}, member).$promise)
		},
		getAbsences : getAbsences,
		saveAbsence : function(absence) {
			return absenceService.save(absence);
		},
		getPresences : function(member) {
			var now = new Date();
			return rx.Observable.range(0, daysInMonth(now.getMonth() + 1, now.getFullYear()))//
			.map(function(index) {
				return new Date(now.getFullYear(), now.getMonth(), index + 1);
			})//
			.toArray()//
			.flatMap(function(listDate){
				return 	getAbsences(member).flatMap(function(absences){
					return rx.Observable.from(listDate).map(function(date){
						return {
							date:date,
							isPresent:isPresent(date, absences)
						}
					})
				});
			})

		}
	};
} ]);