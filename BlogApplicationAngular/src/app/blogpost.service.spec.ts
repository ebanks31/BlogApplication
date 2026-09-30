import { TestBed } from '@angular/core/testing';

import { BlogpostService } from './blogpost.service';

describe('BlogpostService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BlogpostService = TestBed.inject(BlogpostService);
    expect(service).toBeTruthy();
  });
});
