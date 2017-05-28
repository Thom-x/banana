import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Sprint } from '../../models/sprint';

@Component({
  selector: 'app-new-sprint',
  templateUrl: './new-sprint.component.html',
  styleUrls: ['./new-sprint.component.css']
})
export class NewSprintComponent implements OnInit {

  public sprint: Sprint;

  constructor(private activeModal: NgbActiveModal) {
    this.sprint = Sprint.create();
  }

  ngOnInit() {
  }

  save() {
    this.activeModal.close(this.sprint);
  }

  cancel() {
    this.activeModal.dismiss('cancel');
  }
}
