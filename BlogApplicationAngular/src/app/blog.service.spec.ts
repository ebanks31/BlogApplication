import { TestBed, getTestBed } from '@angular/core/testing';
import { BlogService } from './blog.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('BlogService', () => {
  let injector: TestBed;
  let blogService: BlogService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BlogService],
        imports: [HttpClientTestingModule]
      })
      .compileComponents();
      injector = getTestBed();
      blogService = injector.get(blogService);
      httpMock = injector.get(HttpTestingController);

      const req = httpMock.expectOne({ method: 'GET', url: 'http://localhost:9100/blogs' });
      req.flush(dummyBlog);
    
      blogService.getBlogs().subscribe(blogs => {
        expect(blogs.length).toBe(1);
        expect(blogs).toEqual(dummyBlog);
      });
  });

  const dummyBlog = [
    { blogId: 1},
    { blogId: 'title' },
    { blogDescription: 'description' },
    { blog_created_date: '2011-09-09' },
    { blog_terminated_date: '2011-12-09'},
    { status: 'Active' },
    { accountId: 1 },
    { last_updated_date: '2011-11-09' },
  ];

  it('BlogService should be created', () => {
    const service: BlogService = TestBed.get(BlogService);
    expect(service).toBeTruthy();
  });

  it('#getBlogs should return value from blog',
    (done: DoneFn) => {
      blogService.getBlogs().subscribe(value => {
      expect(value).toBe('observable value');
      done();
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
