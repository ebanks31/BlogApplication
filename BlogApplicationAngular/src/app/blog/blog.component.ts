import { Component, OnInit, ViewChild } from '@angular/core';
import { BlogService } from '../blog.service';
import { BlogModel } from '../blog.component';
import { BlogPostModel } from '../blogpost.component';
import { Router, ActivatedRoute } from '@angular/router';
import {
  trigger,
  state,
  style,
  animate,
  transition
} from '@angular/animations';

/**
 * Component
 */
@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.css'],
  animations: [
    trigger('changeDivSize', [
      state('initial', style({
        backgroundColor: 'green',
        width: '100px',
        height: '100px'
      })),
      state('final', style({
        backgroundColor: 'red',
        width: '200px',
        height: '200px'
      })),
      transition('initial=>final', animate('1500ms')),
      transition('final=>initial', animate('1000ms'))
    ])]
})
export class BlogComponent implements OnInit {
  blog: BlogModel;
  blogPost: BlogPostModel;
  blogPosts: BlogPostModel[];
  blogId: number;
  currentBlogPostId: number;
  ckeConfig: any;
  mycontent: string;
  showTextFormattingToolbar: boolean = false;
  saveButtonClick: boolean = false;
  color: string = "light blue";
  @ViewChild("myckeditor") ckeditor: any;
  qtd:any;

  /**
   * on init
   */
  ngOnInit() {
    console.log("this.id: " + this.blogId)
    this.getBlogById(this.blogId);
    this.getBlogsPostsByBlogId(this.blogId);

    console.log("this.blog.blogId: " + this.blogId)

    this.ckeConfig = {
      allowedContent: false,
      extraPlugins: 'divarea',
      forcePasteAsPlainText: true,
      toolbar: 'Basic',
      toolbar_Basic: [
        { name: 'document', groups: ['mode', 'document', 'doctools'], items: ['Source', '-', 'Save', 'NewPage', 'Preview', 'Print', '-', 'Templates'] },
        { name: 'clipboard', groups: ['clipboard', 'undo'], items: ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo'] },
        { name: 'editing', groups: ['find', 'selection', 'spellchecker'], items: ['Find', 'Replace', '-', 'SelectAll', '-', 'Scayt'] },
        { name: 'forms', items: ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'] },
        '/',
        { name: 'basicstyles', groups: ['basicstyles', 'cleanup'], items: ['Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'CopyFormatting', 'RemoveFormat'] },
        { name: 'paragraph', groups: ['list', 'indent', 'blocks', 'align', 'bidi'], items: ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl', 'Language'] },
        { name: 'links', items: ['Link', 'Unlink', 'Anchor'] },
        { name: 'insert', items: ['Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak', 'Iframe'] },
        '/',
        { name: 'styles', items: ['Styles', 'Format', 'Font', 'FontSize'] },
        { name: 'colors', items: ['TextColor', 'BGColor'] },
        { name: 'tools', items: ['Maximize', 'ShowBlocks'] },
        { name: 'others', items: ['-'] },
        { name: 'about', items: ['About'] }
      ]/* you can add more tools to the list */
    };
    console.log("this.ckeConfig : " + this.ckeConfig);
  }

  /**
   * Creates an instance of blog component.
   * @param blogService 
   * @param route 
   * @param router 
   */
  constructor(private blogService: BlogService,
    private route: ActivatedRoute, private router: Router) {
    this.mycontent = `<p>My html content</p>`;


    this.route.params.subscribe(params => this.blogId = params.id,
      error => console.log(error))
  }

  /**
   * Gets blog by id
   * @param blogid 
   */
  getBlogById(blogid: number): void {
    console.log("getBlogById() ")

    this.blogService.getBlogsById(blogid)
      .subscribe(data => this.blog = data,
        error => console.log(error));

    /*
    this.blogService.getBlogsById(id)
    .subscribe(data => console.log(data),
    error => console.log(error));*/

    console.log("this.blog " + this.blog)
  }

  getBlogsPostsByBlogId(blogid: number): void {
    console.log("getBlogsPostsByBlogId() ")

    this.blogService.getBlogPostsByBlogId(blogid)
      .subscribe(data => this.blogPosts = data,
        error => console.log(error));

    /*
    this.blogService.getBlogsPostsByBlogId(id)
    .subscribe(data => console.log("getBlogsPostsByBlogId(): data" + data),
    error => console.log(error));*/

    console.log("this.blogPost " + this.blogPosts)
  }

    /**
   * Gets blog by id
   * @param blogid 
   */
  deleteByBlogPostAndBlogId(blogpostId: number, blogid: number): void {
    console.log("getBlogById() ")

    this.blogService.deleteBlogPost(blogpostId, blogid)
      .subscribe(data => this.blog = data,
        error => console.log(error));

    /*
    this.blogService.getBlogsById(id)
    .subscribe(data => console.log(data),
    error => console.log(error));*/

    console.log("this.blog " + this.blog)
  }

  /**
   * Converts to json
   * @param account 
   * @returns  
   */
  ConvertToJSON(account: any) {
    console.log("account " + account);
    return JSON.parse(account);
  }
  /**
   * Determines whether change on
   * @param $event 
   * @param myForm 
   */
  onChange($event: any, myForm): void {
    console.log("onChange");
    console.log("myForm " + myForm.invalid);
    this.mycontent = myForm;
  }

  /**
   * Adds a blog post
   * @param $event 
   */
  addBlogPost($event: any): void {
    this.router.navigate(['blogs/blog/' + this.blogId + "/posts/post/add"]);
  }

  /**
   * Edit a blog post
   * @param $event 
   */
  editBlogPost(blogPostId: number): void {
    this.router.navigate(['blogs/blog/' + this.blogId + "/posts/" + blogPostId]);
  }  

  /**
   * Deletes a blog post
   * @param $event 
   */
  deleteBlogPost(blogpostId: number): void {
    console.log("deleteBlogPost: ");

    console.log("blogpostId: " + blogpostId);
    console.log("this.blogId: " + this.blogId);


    this.deleteByBlogPostAndBlogId(blogpostId, this.blogId);
    this.router.navigate(['blog/' + this.blogId]);
  }
}