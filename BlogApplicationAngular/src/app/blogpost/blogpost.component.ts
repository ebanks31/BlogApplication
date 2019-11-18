import { Component, OnInit } from "@angular/core";
import { BlogPostModel } from "../blogpost.component";
import { ActivatedRoute } from "@angular/router";
import { BlogService } from "../blog.service";
import {
  trigger,
  state,
  style,
  animate,
  transition
} from "@angular/animations";

@Component({
  selector: "app-blogpost",
  templateUrl: "./blogpost.component.html",
  styleUrls: ["./blogpost.component.css"],
  animations: [
    trigger("changeDivSize", [
      state(
        "initial",
        style({
          backgroundColor: "green",
          width: "100px",
          height: "100px"
        })
      ),
      state(
        "final",
        style({
          backgroundColor: "red",
          width: "200px",
          height: "200px"
        })
      ),
      transition("initial=>final", animate("1500ms")),
      transition("final=>initial", animate("1000ms"))
    ])
  ]
})
export class BlogPostComponent implements OnInit {
  blogPost: BlogPostModel;
  blogId: any;
  blogPostId: any;
  ckeConfig: any;
  blogPostMessage: string;
  showTextFormattingToolbar: boolean = false;
  saveButtonClick: boolean = false;
  color: string = "light blue";
  blogPostTitle: string;

  constructor(private blogService: BlogService, private route: ActivatedRoute) {
    this.route.params.subscribe(
      params => (this.blogId = params.blogId),
      error => console.log(error)
    );

    this.route.params.subscribe(
      data => (this.blogPostId = data.blogPostId),
      error => console.log(error)
    );
  }

  ngOnInit() {
    this.getBlogPostByBlogId(this.blogId, this.blogPostId);
    this.ckeConfig = {
      allowedContent: false,
      extraPlugins: "divarea",
      forcePasteAsPlainText: true,
      toolbar: "Basic",
      toolbar_Basic: [
        {
          name: "document",
          groups: ["mode", "document", "doctools"],
          items: [
            "Source",
            "-",
            "Save",
            "NewPage",
            "Preview",
            "Print",
            "-",
            "Templates"
          ]
        },
        {
          name: "clipboard",
          groups: ["clipboard", "undo"],
          items: [
            "Cut",
            "Copy",
            "Paste",
            "PasteText",
            "PasteFromWord",
            "-",
            "Undo",
            "Redo"
          ]
        },
        {
          name: "editing",
          groups: ["find", "selection", "spellchecker"],
          items: ["Find", "Replace", "-", "SelectAll", "-", "Scayt"]
        },
        {
          name: "forms",
          items: [
            "Form",
            "Checkbox",
            "Radio",
            "TextField",
            "Textarea",
            "Select",
            "Button",
            "ImageButton",
            "HiddenField"
          ]
        },
        "/",
        {
          name: "basicstyles",
          groups: ["basicstyles", "cleanup"],
          items: [
            "Bold",
            "Italic",
            "Underline",
            "Strike",
            "Subscript",
            "Superscript",
            "-",
            "CopyFormatting",
            "RemoveFormat"
          ]
        },
        {
          name: "paragraph",
          groups: ["list", "indent", "blocks", "align", "bidi"],
          items: [
            "NumberedList",
            "BulletedList",
            "-",
            "Outdent",
            "Indent",
            "-",
            "Blockquote",
            "CreateDiv",
            "-",
            "JustifyLeft",
            "JustifyCenter",
            "JustifyRight",
            "JustifyBlock",
            "-",
            "BidiLtr",
            "BidiRtl",
            "Language"
          ]
        },
        { name: "links", items: ["Link", "Unlink", "Anchor"] },
        {
          name: "insert",
          items: [
            "Image",
            "Flash",
            "Table",
            "HorizontalRule",
            "Smiley",
            "SpecialChar",
            "PageBreak",
            "Iframe"
          ]
        },
        "/",
        { name: "styles", items: ["Styles", "Format", "Font", "FontSize"] },
        { name: "colors", items: ["TextColor", "BGColor"] },
        { name: "tools", items: ["Maximize", "ShowBlocks"] },
        { name: "others", items: ["-"] },
        { name: "about", items: ["About"] }
      ] /* you can add more tools to the list */
    };

    console.log("this.ckeConfig : " + this.ckeConfig);
  }

  getBlogPostByBlogId(blogid: number, blogPostId: number): void {
    console.log("getBlogsPostByBlogIdAndBlogPostId() ");
    console.log("blogid: " + blogid);
    console.log("blogPostId: " + blogPostId);

    this.blogService.getBlogPostByBlogId(blogid, blogPostId).subscribe(
      data => (this.blogPost = data),
      error => console.log(error)
    );

    /*
    this.blogService.getBlogsPostsByBlogId(id)
    .subscribe(data => console.log("getBlogsPostsByBlogId(): data" + data),
    error => console.log(error));*/

    console.log("this.blogPost " + this.blogPost);
  }

  editPost($event: any): void {
    this.showTextFormattingToolbar = true;
    this.saveButtonClick = false;
    console.log("editPost");
  }

  savePost($event: any): void {
    this.showTextFormattingToolbar = false;
    this.saveButtonClick = true;

    console.log("this.blogPost.blogPostBody: " + this.blogPost.blogPostBody);
    console.log("this.blogPost.blogPostTitle: " + this.blogPost.blogPostTitle);
    console.log("this.blogPostMessage: " + this.blogPostMessage);
    console.log("this.blogPostMessage: " + this.blogPostMessage);

    this.blogService
      .saveBlogPost(this.blogId, this.blogPostId, this.blogPost)
      .subscribe(
        data => console.log(data),
        error => console.log(error)
      );
    console.log("savePost");
  }

  onChange($event: any): void {
    console.log("onChanged");
  }
}