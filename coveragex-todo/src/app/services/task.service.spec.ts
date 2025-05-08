import { TestBed } from '@angular/core/testing';
import { TaskService } from './task.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Task } from '../models/addTask.model';
import { environment } from '../../environment/environment';

describe('TaskService', () => {
  let service: TaskService;
  let httpMock: HttpTestingController;
  const apiUrl = `${environment.apiUrl}/tasks`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TaskService]
    });
    service = TestBed.inject(TaskService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get recent tasks', () => {
    const mockTasks: Task[] = [
      { id: 1, title: 'Task 1', description: 'Description 1' },
      { id: 2, title: 'Task 2', description: 'Description 2' }
    ];

    service.getRecentTasks().subscribe(tasks => {
      expect(tasks).toEqual(mockTasks);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('GET');
    req.flush(mockTasks);
  });

  it('should add a new task', () => {
    const newTask = {
      title: 'New Task',
      description: 'New Description'
    };

    const mockResponse: Task = {
      id: 1,
      title: newTask.title,
      description: newTask.description
    };

    service.addTask(newTask.title, newTask.description);

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newTask);
    req.flush(mockResponse);
  });

  it('should mark task as completed', () => {
    const taskId = 1;
    const mockResponse: Task = {
      id: taskId,
      title: 'Task',
      description: 'Description'
    };

    service.markAsCompleted(taskId);

    const req = httpMock.expectOne(`${apiUrl}/${taskId}/complete`);
    expect(req.request.method).toBe('PUT');
    req.flush(mockResponse);
  });

  it('should refresh tasks after adding a new task', () => {
    const newTask = {
      title: 'New Task',
      description: 'New Description'
    };

    const mockResponse: Task = {
      id: 1,
      title: newTask.title,
      description: newTask.description
    };

    const mockTasks: Task[] = [mockResponse];

    service.addTask(newTask.title, newTask.description);

    // Handle POST request
    const postReq = httpMock.expectOne(apiUrl);
    expect(postReq.request.method).toBe('POST');
    postReq.flush(mockResponse);

    // Handle GET request for refresh
    const getReq = httpMock.expectOne(apiUrl);
    expect(getReq.request.method).toBe('GET');
    getReq.flush(mockTasks);
  });

  it('should refresh tasks after marking task as completed', () => {
    const taskId = 1;
    const mockTasks: Task[] = [
      { id: 2, title: 'Task 2', description: 'Description 2' }
    ];

    service.markAsCompleted(taskId);

    // Handle PUT request
    const putReq = httpMock.expectOne(`${apiUrl}/${taskId}/complete`);
    expect(putReq.request.method).toBe('PUT');
    putReq.flush({ id: taskId, title: 'Task', description: 'Description' });

    // Handle GET request for refresh
    const getReq = httpMock.expectOne(apiUrl);
    expect(getReq.request.method).toBe('GET');
    getReq.flush(mockTasks);
  });

  it('should handle error when getting tasks', () => {
    const errorMessage = 'Server error';
    spyOn(console, 'error');

    service.getRecentTasks().subscribe({
      error: (error) => {
        expect(error.status).toBe(500);
      }
    });

    const req = httpMock.expectOne(apiUrl);
    req.flush(errorMessage, { status: 500, statusText: 'Server Error' });
    expect(console.error).toHaveBeenCalled();
  });
});
