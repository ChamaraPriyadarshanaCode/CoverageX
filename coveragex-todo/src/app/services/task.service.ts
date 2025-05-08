import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Task } from '../models/addTask.model';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiUrl = `${environment.apiUrl}/tasks`;
  private tasksSubject = new BehaviorSubject<Task[]>([]);
  tasks$ = this.tasksSubject.asObservable();

  constructor(private http: HttpClient) {
    this.refreshTasks(); // Load tasks when service is initialized
  }

  getRecentTasks(): Observable<Task[]> {
    this.refreshTasks();
    return this.tasks$;
  }

  addTask(title: string, description: string): void {
    const task = {
      title,
      description
    };
    
    this.http.post<Task>(this.apiUrl, task).subscribe({
      next: () => {
        this.refreshTasks();
      },
      error: (error) => {
        console.error('Error adding task:', error);
      }
    });
  }

  markAsCompleted(id: number): void {
    this.http.put<Task>(`${this.apiUrl}/${id}/complete`, {}).subscribe({
      next: () => {
        this.refreshTasks();
      },
      error: (error) => {
        console.error('Error completing task:', error);
      }
    });
  }

  private refreshTasks(): void {
    this.http.get<Task[]>(this.apiUrl).subscribe({
      next: (tasks) => {
        this.tasksSubject.next(tasks);
      },
      error: (error) => {
        console.error('Error fetching tasks:', error);
      }
    });
  }
}