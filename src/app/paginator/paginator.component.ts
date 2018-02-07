import { Component, OnInit , Injectable , Input , Output , EventEmitter} from '@angular/core';
import {ViewComponentComponent} from 'app/view-component/view-component.component'
@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.css']
})
@Injectable()
export class PaginatorComponent implements OnInit {
  @Input() page : number;
  @Input() size :number;
  constructor(private view:ViewComponentComponent) { }

  ngOnInit() {
  }
  next(){
    if(this.view.page < this.view.size)
    {
      this.view.page+=1;
      this.view.onPaginatorChange();
    }
  }
  back(){
    if(this.view.page > 1)
    {
      this.view.page-=1;
      this.view.onPaginatorChange();
    }
  }


}
