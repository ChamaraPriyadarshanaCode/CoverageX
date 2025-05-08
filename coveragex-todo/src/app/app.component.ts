import { Component } from '@angular/core';
import { AddTaskFormComponent } from './components/add-task-form/add-task-form/add-task-form.component';
import { PendingListComponent } from './components/pending-task-list/pending-list/pending-list.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [AddTaskFormComponent, PendingListComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'coveragex-task';
}
