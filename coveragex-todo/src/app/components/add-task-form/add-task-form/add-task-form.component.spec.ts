import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddTaskFormComponent } from './add-task-form.component';
import { TaskService } from '../../../services/task.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';

describe('AddTaskFormComponent', () => {
  let component: AddTaskFormComponent;
  let fixture: ComponentFixture<AddTaskFormComponent>;
  let taskService: TaskService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        BrowserAnimationsModule,
        AddTaskFormComponent
      ],
      providers: [TaskService]
    }).compileComponents();

    fixture = TestBed.createComponent(AddTaskFormComponent);
    component = fixture.componentInstance;
    taskService = TestBed.inject(TaskService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with empty form', () => {
    expect(component.taskForm.get('title')?.value).toBe('');
    expect(component.taskForm.get('description')?.value).toBe('');
  });

  it('should validate required fields', () => {
    const form = component.taskForm;
    expect(form.valid).toBeFalsy();
    expect(form.get('title')?.errors?.['required']).toBeTruthy();
    expect(form.get('description')?.errors?.['required']).toBeTruthy();
  });

  it('should validate title max length', () => {
    const titleControl = component.taskForm.get('title');
    titleControl?.setValue('a'.repeat(51));
    expect(titleControl?.errors?.['maxlength']).toBeTruthy();
    
    titleControl?.setValue('a'.repeat(50));
    expect(titleControl?.errors?.['maxlength']).toBeFalsy();
  });

  it('should validate description max length', () => {
    const descControl = component.taskForm.get('description');
    descControl?.setValue('a'.repeat(251));
    expect(descControl?.errors?.['maxlength']).toBeTruthy();
    
    descControl?.setValue('a'.repeat(250));
    expect(descControl?.errors?.['maxlength']).toBeFalsy();
  });

  it('should show error messages when fields are touched and invalid', () => {
    const titleControl = component.taskForm.get('title');
    titleControl?.markAsTouched();
    fixture.detectChanges();
    
    const titleError = fixture.debugElement.query(By.css('mat-error'));
    expect(titleError.nativeElement.textContent.trim()).toBe('Title is required');
  });

  it('should call taskService.addTask when form is valid and submitted', () => {
    const addTaskSpy = spyOn(taskService, 'addTask');
    const testTitle = 'Test Task';
    const testDesc = 'Test Description';

    component.taskForm.setValue({
      title: testTitle,
      description: testDesc
    });

    component.onSubmit();
    expect(addTaskSpy).toHaveBeenCalledWith(testTitle, testDesc);
  });

  it('should reset form after successful submission', () => {
    spyOn(taskService, 'addTask');
    component.taskForm.setValue({
      title: 'Test Task',
      description: 'Test Description'
    });

    component.onSubmit();

    expect(component.taskForm.get('title')?.value).toBe('');
    expect(component.taskForm.get('description')?.value).toBe('');
    expect(component.taskForm.pristine).toBeTrue();
    expect(component.taskForm.untouched).toBeTrue();
  });

  it('should not call taskService.addTask when form is invalid', () => {
    const addTaskSpy = spyOn(taskService, 'addTask');
    component.onSubmit();
    expect(addTaskSpy).not.toHaveBeenCalled();
  });

  it('should return correct error messages', () => {
    expect(component.getErrorMessage('title')).toBe('Title is required');
    
    const titleControl = component.taskForm.get('title');
    titleControl?.setValue('a'.repeat(51));
    expect(component.getErrorMessage('title')).toBe('Title cannot exceed 50 characters');
  });
});
