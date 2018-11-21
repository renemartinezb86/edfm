import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { EdfmApplicationModule } from './application/application.module';
import { EdfmApplicationVersionModule } from './application-version/application-version.module';
import { EdfmApplicationVersionRelationModule } from './application-version-relation/application-version-relation.module';
import { EdfmEnvironmentModule } from './environment/environment.module';
import { EdfmEnvironmentTypeModule } from './environment-type/environment-type.module';
import { EdfmDeployPipelineModule } from './deploy-pipeline/deploy-pipeline.module';
import { EdfmDeploymentModule } from './deployment/deployment.module';
import { EdfmDeploymentPipelineLogModule } from './deployment-pipeline-log/deployment-pipeline-log.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        EdfmApplicationModule,
        EdfmApplicationVersionModule,
        EdfmApplicationVersionRelationModule,
        EdfmEnvironmentModule,
        EdfmEnvironmentTypeModule,
        EdfmDeployPipelineModule,
        EdfmDeploymentModule,
        EdfmDeploymentPipelineLogModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmEntityModule {}
