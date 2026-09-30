# Blog Application

A client-side blog application built with Angular. Users can browse blogs and posts, create and edit content, and view account-related pages. The frontend talks to a separate HTTP API; that backend is not included in this repository.

## Features

- Browse the blog list, open a blog, and view its posts.
- Create blogs and blog posts, edit posts, and delete posts.
- View account lists and account details.
- Use the About and Contact pages.
- Edit post content with the CKEditor 4 Angular integration.
- Run component and service tests with Jasmine and Karma.

## Technology

- Angular `22.2.x` and Angular CLI `22.2.x`
- TypeScript `6.0.x`, RxJS `7.8.x`, and Zone.js `0.16.x`
- Bootstrap 4, ngx-bootstrap, and CKEditor 4
- Karma, Jasmine, and Chrome for unit tests

## Requirements

- Node.js `22.22.3` through `22.x`, `24.15.0` through `24.x`, or `26.0.0+`.
- npm 8 or later.
- Google Chrome installed to run the Karma test suite with the current configuration.
- The blog API available at `http://localhost:9600` for live data workflows.

## Get Started

Install dependencies and start the development server:

```sh
npm install
npm start
```

Open [http://localhost:4200](http://localhost:4200). The Angular development server rebuilds the app when source files change.

## Routes

| Path | Page |
| --- | --- |
| `/accounts` | Account list |
| `/accounts/:id` | Account details |
| `/account` | Account page |
| `/home` | Redirect to the application root |
| `/blogs` | Blog list |
| `/blogs/addBlog` | Create a blog |
| `/blogs/blog/:id` | Blog details and post list |
| `/blogs/blog/:blogId/posts/:blogPostId` | Blog post details/editing |
| `/blogs/blog/:blogId/posts/post/add` | Create a post for a blog |
| `/blog/:id` | Blog details alias |
| `/about` | About page |
| `/contact` | Contact page |

The route configuration is in `src/app/app-routing.module.ts`. `AppComponent` provides the navigation shell and its `<router-outlet>` hosts the selected page.

## Application Structure

- `src/app/` contains the Angular module, routes, components, models, and injectable services.
- `src/app/blog/`, `src/app/blogs/`, and `src/app/blogpost/` implement blog and post views.
- `src/app/add-blog/` and `src/app/add-blog-post/` implement creation forms.
- `src/app/accounts/` and `src/app/account/` implement account views.
- `src/app/blog.service.ts`, `account.service.ts`, and `user.service.ts` make HTTP requests through Angular `HttpClient`.
- `src/app/**/*.spec.ts` and `src/contact.service.spec.ts` contain unit tests.
- `docs/application-flow.md` describes the navigation and data flow.

The application uses an NgModule-based setup: `AppModule` declares the components and imports routing, forms, HTTP, animations, and CKEditor modules.

## API Connection

The services currently use a development API base at `http://localhost:9600`; this value is hard-coded in the service classes rather than configured through Angular environments. Start the backend separately before using pages that load or submit data.

| Service | Operations |
| --- | --- |
| `AccountService` | List accounts and fetch an account by ID |
| `BlogService` | List/get blogs and posts; add blogs/posts; update or delete posts |
| `UserService` | List/get users; add, save, edit, or delete users |

`BlogpostService`, `CommentService`, and `ContactService` are injectable placeholders and currently expose no API methods. Account authorization is also currently assembled in client-side code; do not treat those credentials as production secrets. Move authentication to a secure backend before deployment.

CKEditor 4 is loaded from a CDN in `src/index.html`, so that script requires network access. Review the editor version, licensing, and deployment policy before shipping.

## Build and Test

Build the development configuration:

```sh
npm run build
```

Build an optimized production bundle:

```sh
npm run build -- --configuration production
```

Run unit tests in watch mode:

```sh
npm test
```

Run once without watch mode:

```sh
npm test -- --no-watch
```

The Karma configuration launches Chrome. Unit tests use mocked services for component behavior and `HttpTestingController` for HTTP service contracts.

## Generated Documentation

The package scripts include Compodoc and TypeDoc commands:

```sh
npm run generate-docs
npm run serve-docs
```

The generated documentation is not required to build or run the app.

## Development Notes

- Keep API URLs in sync with the backend. Moving them to Angular environment configuration would make local, test, and production endpoints easier to manage.
- The repository does not currently define lint or end-to-end test scripts; the former Protractor instructions no longer apply.
- Use `npx ng generate component <name>` or the corresponding Angular CLI schematic to scaffold additional Angular code.

For the application workflow diagram, see [docs/application-flow.md](docs/application-flow.md).
