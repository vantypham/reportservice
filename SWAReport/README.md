## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.
Run `ng build --configuration production` to build the project with production config. The build artifacts will be stored in the `dist/` directory.

## Build with Docker image
Since angular browser app cannot read environment variables, we need to build the app with correct environment 
variables inside src/environments/environment.prod.ts file.

Then run `docker-compose build -t ReportUI .` to build the docker image.