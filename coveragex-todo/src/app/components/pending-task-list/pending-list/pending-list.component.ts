import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Task } from '../../../models/addTask.model';
import { TaskService } from '../../../services/task.service';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { NgFor, AsyncPipe } from '@angular/common';
import { FormsModule} from '@angular/forms';
@Component({
  standalone: true,
  selector: 'app-pending-list',
  imports: [MatCardModule, MatButtonModule, NgFor, AsyncPipe,FormsModule],
  templateUrl: './pending-list.component.html',
  styleUrls: ['./pending-list.component.css']
})
export class PendingListComponent implements OnInit {
  todos$: Observable<Task[]>;

  constructor(private todoService: TaskService) {
    this.todos$ = this.todoService.getRecentTasks();
  }

  ngOnInit(): void {}

  markAsDone(id: number): void {
    this.todoService.markAsCompleted(id);
  }
}