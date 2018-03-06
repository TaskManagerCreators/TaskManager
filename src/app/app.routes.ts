import { Routes } from "@angular/router";
import { UsersComponent } from './users/users.component';
import { UshistoryComponent } from './ushistory/ushistory.component';
import { ErrorsComponent } from './errors/errors.component';

export const routes: Routes = [
  {
    path: "users",
    component: UsersComponent,
  },
  {
    path: "errors",
    component: ErrorsComponent,
  },
  {
    path: "history",
    component: UshistoryComponent,
  },
];
