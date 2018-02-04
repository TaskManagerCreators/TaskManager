import {Injectable} from '@angular/core';
import {HttpClient , HttpHeaders} from '@angular/common/http';
import { UUID } from 'angular2-uuid';


const requestOptions = {
  headers: new Headers(this.headerDict),
};

@Injectable()
export class HttpService{

    constructor(private http: HttpClient){ }

    uuid : UUID = UUID.UUID();

    getData(){
      var result = this.http.get('http://localhost:8080/tasks' ,   headers: new HttpHeaders({'Content-Type': 'application/json' ,
                                  'Accept': 'application/json',
                                'Access-Control-Allow-Headers': 'Content-Type',
                              'Access-Control-Allow-Origin' : '*'}),
        observe: 'response'
      });
        return result;
    }
    getDataByName(name: string){
      var result = this.http.get('http://localhost:8080/tasks?name='+name );
      return result;
    }

    postData(date : string){
      let uuid = UUID.UUID();
      return this.http.post(
        'http://localhost:8080/tasks',
      JSON.stringify({
        id : uuid ,
        name : 'generator' ,
        describe : 'purpose' ,
        targetTime : date ,
        reaction : {
          type : 'OUTPUT' ,
          value : 'test'
        } ,
        contacts : [] ,
        status : 'SCHEDULED'
      })
      , {
      headers: new HttpHeaders({'Content-Type': 'application/json' ,
                                'Accept': 'application/json',
                              'Access-Control-Allow-Headers': 'Content-Type',
                            'Access-Control-Allow-Origin' : '*'}),
      observe: 'response'
    })
    }
}
