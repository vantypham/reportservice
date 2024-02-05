# Use an official Node runtime as a base image
FROM node:18 as builder
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the entire application to the working directory
COPY . .

# Build the Angular app for production
RUN npm run build:prod

# Stage 2: Serve Angular app using Nginx
FROM nginx:alpine

# Copy the production-ready code from the builder stage to Nginx
COPY --from=builder /app/dist /usr/share/nginx/html

# Expose port 80 for the application
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]

#docker run  -p 80:80 -v dist_vol:/usr/share/nginx/html reportui