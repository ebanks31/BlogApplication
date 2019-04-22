import { Component, OnInit, ViewChild } from '@angular/core';

/**
 * Component
 */
@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  name = 'ng2-ckeditor';
  ckeConfig: any;
  mycontent: string;
  log: string = '';
  @ViewChild("myckeditor") ckeditor: any;

  constructor() {
    this.mycontent = `<p>My html content</p>`;
  }

  /**
   * on init
   */
  ngOnInit() {
    this.ckeConfig = {
      allowedContent: false,
      extraPlugins: 'divarea',
      forcePasteAsPlainText: true,
      toolbar: 'Basic',
      toolbar_Basic: ['Bold', 'Italic'] /* you can add more tools to the list */
    };
  }

  /**
   * Determines whether change on
   * @param $event 
   */
  onChange($event: any): void {
    console.log("onChange");
    //this.log += new Date() + "<br />";
  }
}
