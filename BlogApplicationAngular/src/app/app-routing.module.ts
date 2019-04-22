import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AccountComponent } from './account/account.component';
import { AccountsComponent } from './accounts/accounts.component';
import { BlogComponent } from './blog/blog.component';
import { BlogsComponent } from './blogs/blogs.component';
import { BlogPostComponent } from './blogpost/blogpost.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';
import { AddBlogComponent } from './add-blog/add-blog.component';
import { AddBlogPostComponent } from './add-blog-post/add-blog-post.component';

const routes: Routes = [
  { path: '', redirectTo: '/', pathMatch: 'full' },
  { path: 'home', redirectTo: '/', pathMatch: 'full' },
  { path: 'account', component: AccountComponent },
  { path: 'accounts/:id', component: AccountComponent },
  { path: 'accounts', component: AccountsComponent, pathMatch: 'full' },
  { path: 'blogs', component: BlogsComponent },
  { path: 'blogs/blog/:id', component: BlogComponent },
  { path: 'blogs/blog/:blogId/posts/:blogPostId', component: BlogPostComponent, pathMatch: 'full'  },
  { path: 'blog/:id', component: BlogComponent },
  { path: 'about', component: AboutComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'blogs/addBlog', component: AddBlogComponent },
  { path: 'blogs/blog/:blogId/posts/post/add', component: AddBlogPostComponent }
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [ RouterModule ]
})

export class AppRoutingModule { }
