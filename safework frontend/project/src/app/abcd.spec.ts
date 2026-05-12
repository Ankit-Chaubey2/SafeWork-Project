import { TestBed } from '@angular/core/testing';

import { Abcd } from './abcd';

describe('Abcd', () => {
  let service: Abcd;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Abcd);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
