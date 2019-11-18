import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms"; // <-- NgModel lives here
import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { AboutComponent } from "./about/about.component";
import { HomeComponent } from "./home/home.component";
import { DeveloperComponent } from "./developer/developer.component";
import { AccountComponent } from "./account/account.component";
import { BlogComponent } from "./blog/blog.component";
import { BlogsComponent } from "./blogs/blogs.component";
import { AccountsComponent } from "./accounts/accounts.component";
import { HttpClientModule } from "@angular/common/http";
import { ContactComponent } from "./contact/contact.component";
import { BlogPostComponent } from "./blogpost/blogpost.component";
import { CKEditorModule } from "ng2-ckeditor";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { AddBlogComponent } from "./add-blog/add-blog.component";
import { AddBlogPostComponent } from "./add-blog-post/add-blog-post.component";

@NgModule({
  declarations: [
    AppComponent,
    AboutComponent,
    HomeComponent,
    DeveloperComponent,
    AccountComponent,
    BlogComponent,
    BlogsComponent,
    AccountsComponent,
    ContactComponent,
    BlogPostComponent,
    AddBlogComponent,
    AddBlogPostComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    CKEditorModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
