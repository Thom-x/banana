import { Links } from './links';
import { Observable, Subject } from 'rxjs/Rx';
import { DateUtils } from '../services/date.service';
export class Sprint {


    static create(object?: any): Sprint {
        if (object != null) {
            return new Sprint(object.id,
                object.name,
                DateUtils.getDateIfPresent(object.start),
                DateUtils.getDateIfPresent(object.end),
                object.businessValue,
                object.complexity,
                object.engagedComplexity,
                object.closedComplexity,
                object._links);
        }
        return new Sprint(null, null, new Date(), null, 0, 0, 0, 0, null);
    }


    constructor(public id: string,
        public name: string,
        public start: Date,
        public end: Date,
        public businessValue: number,
        public complexity: number,
        public engagedComplexity: number,
        public closedComplexity: number,
        public _links: Links) {
        if (this.start != null) {
            this.start.setHours(0, 0, 0, 0);
        }
        if (this.end != null) {
            this.end.setHours(0, 0, 0, 0);
        }
    }


    public getDates(): Observable<Date> {
        return Observable.range(0, DateUtils.dayDiff(this.start, this.end))//
            .map(index => new Date(this.start.getTime() + (index * 1000 * 60 * 60 * 24)));
    }


    public getComplexityPerDay(): Observable<number> {
        return this.getDates()//
            .filter(date => !DateUtils.isWeekend(date))
            .count()//
            .map(nbDays => this.engagedComplexity / nbDays);
    }

}