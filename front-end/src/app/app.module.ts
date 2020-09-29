import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HeaderComponent} from './header/header.component';
import { ProjectsListComponent } from './projects-list/projects-list.component';
import { ListProjectComponent } from './projects-list/list-projects/list-project.component';
import { NewProjectComponent } from './projects-list/new-project/new-project.component';
import {ProjectService} from './projects-list/project.service';
import { ProjectItemComponent } from './projects-list/list-projects/project-item/project-item.component';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { ErrorPageComponent } from './error-page/error-page.component';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ProjectsListComponent,
    ListProjectComponent,
    NewProjectComponent,
    ProjectItemComponent,
    ErrorPageComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  exports: [
    HeaderComponent
  ],
  providers: [ProjectService],
  bootstrap: [AppComponent]
})
export class AppModule { }

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
