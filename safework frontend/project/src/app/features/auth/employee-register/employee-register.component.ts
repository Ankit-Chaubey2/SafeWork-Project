import { Component, inject, signal } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService, EmployeeRegistrationRequest } from '../../../core/services/auth.service';

@Component({
  selector: 'app-employee-register',
  standalone: true,
  imports: [NgIf, NgFor, FormsModule, RouterLink],
  templateUrl: './employee-register.component.html',
  styleUrl: './employee-register.component.css'
})
export class EmployeeRegisterComponent {
  private auth = inject(AuthService);
  readonly router = inject(Router);

  loading = signal(false);
  error = signal('');
  success = signal('');
  selectedFile: File | null = null;

  genders = ['Male', 'Female', 'Other'];
  departments = ['Manufacturing', 'Safety', 'Operations', 'Compliance', 'Maintenance', 'Warehouse', 'Quality'];

  form: EmployeeRegistrationRequest = {
    userName: '',
    userEmail: '',
    userContact: '',
    password: '',
    employeeDOB: '',
    employeeGender: '',
    employeeAddress: '',
    employeeDepartmentName: '',
    employeeDocumentType: '',
    employeeFileURL: '',
  };

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  async submit(): Promise<void> {
    this.error.set('');
    this.success.set('');

    if (!this.selectedFile) {
      this.error.set('Please select a document to upload.');
      return;
    }

    this.loading.set(true);

    const formData = new FormData();
    // Wrap JSON in a Blob with application/json type for Spring @RequestPart
    formData.append('employee', new Blob([JSON.stringify(this.form)], { type: 'application/json' }));
    formData.append('file', this.selectedFile);

    const result = await this.auth.registerEmployee(formData);
    this.loading.set(false);

    if (!result.success) {
      this.error.set(result.message);
      return;
    }

    this.success.set(result.message);
    setTimeout(() => this.router.navigate(['/login']), 1200);
  }
}

