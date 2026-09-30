# Application Flow

## Navigation

The browser loads the Angular shell. The router displays the selected page inside `AppComponent`'s router outlet. Blog and account actions are handled by their feature components.

```mermaid
flowchart TD
    Browser[Browser] --> Shell[AppComponent navigation shell]
    Shell --> Router{Angular Router}

    Router --> AccountsList["/accounts<br/>AccountsComponent"]
    Router --> AccountDetail["/accounts/:id<br/>AccountComponent"]
    Router --> Account["/account<br/>AccountComponent"]
    Router --> BlogList["/blogs<br/>BlogsComponent"]
    Router --> BlogCreate["/blogs/addBlog<br/>AddBlogComponent"]
    Router --> BlogDetail["/blogs/blog/:id<br/>BlogComponent"]
    Router --> PostCreate["/blogs/blog/:blogId/posts/post/add<br/>AddBlogPostComponent"]
    Router --> PostDetail["/blogs/blog/:blogId/posts/:blogPostId<br/>BlogPostComponent"]
    Router --> About["/about<br/>AboutComponent"]
    Router --> Contact["/contact<br/>ContactComponent"]

    BlogList -->|Select a blog| BlogDetail
    BlogDetail -->|Select a post| PostDetail
    BlogDetail -->|Add a post| PostCreate
    BlogDetail -->|Delete a post| BlogDetail
    BlogCreate -->|Save blog| BlogList
    PostCreate -->|Save post| BlogDetail
    PostDetail -->|Save post| PostDetail
```

## Data Requests

Feature components call injectable services. Services use Angular `HttpClient` to exchange JSON with the separate API currently configured at `http://localhost:9600`.

```mermaid
flowchart LR
    subgraph BrowserApp[Angular application]
        AccountPages[AccountsComponent<br/>AccountComponent]
        BlogPages[BlogsComponent<br/>BlogComponent<br/>BlogPostComponent]
        CreatePages[AddBlogComponent<br/>AddBlogPostComponent]
        UserPage[AccountComponent user lookup]

        AccountPages --> AccountService[AccountService]
        BlogPages --> BlogService[BlogService]
        CreatePages --> BlogService
        UserPage --> UserService[UserService]

        AccountService --> HttpClient
        BlogService --> HttpClient
        UserService --> HttpClient
    end

    HttpClient[Angular HttpClient] -->|HTTP request| API[Blog API<br/>localhost:9600]
    API -->|JSON response| HttpClient
    HttpClient -->|Observable result| AccountService
    HttpClient -->|Observable result| BlogService
    HttpClient -->|Observable result| UserService
    AccountService -->|Update view state| AccountPages
    BlogService -->|Update view state| BlogPages
    BlogService -->|Create/update requests| CreatePages
    UserService -->|Update account view| UserPage
```

## Main Operations

| User action | Component | Service operation |
| --- | --- | --- |
| Open the blog list | `BlogsComponent` | `BlogService.getBlogs()` |
| Open a blog | `BlogComponent` | `getBlogsById()` and `getBlogPostsByBlogId()` |
| Open a post | `BlogPostComponent` | `getBlogPostByBlogId()` |
| Create a blog | `AddBlogComponent` | `BlogService.addBlog()` |
| Create a post | `AddBlogPostComponent` | `BlogService.addBlogPost()` |
| Edit or remove a post | `BlogPostComponent` or `BlogComponent` | `saveBlogPost()` or `deleteBlogPost()` |
| View accounts | `AccountsComponent` | `AccountService.getAccounts()` |
| View account details | `AccountComponent` | `getAccountsById()` and `UserService.getUserById()` |
