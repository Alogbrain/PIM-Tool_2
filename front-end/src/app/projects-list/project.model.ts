import {Group} from './group.model';

export interface Project {
  projectNumber: number;
  name: string;
  customer: string;
  group: Group;
  members: string;
  status: string;
  startDate: string;
  endDate: string;
}
