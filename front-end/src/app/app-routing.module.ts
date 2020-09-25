import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


import {ProjectsListComponent} from './projects-list/projects-list.component';
import {ListProjectComponent} from './projects-list/list-projects/list-project.component';
import {NewProjectComponent} from './projects-list/new-project/new-project.component';

const routes: Routes = [
  { path: '', redirectTo: '/projects', pathMatch: 'full' },
  { path: 'projects',
    component: ProjectsListComponent,
    children: [
      { path: 'new-project', component: NewProjectComponent },
      { path: 'projects-list', component: ListProjectComponent},
      { path: ':id/edit', component: NewProjectComponent}
    ]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
