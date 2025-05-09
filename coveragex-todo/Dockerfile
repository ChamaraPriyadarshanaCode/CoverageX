# Stage 1: Build the Angular application
FROM node:18 AS build

WORKDIR /app

# Install dependencies
COPY package*.json ./

# Install app dependencies
RUN ["npm", "ci"]

# Install Angular CLI globally
RUN ["npm", "install", "-g", "@angular/cli"]

# Copy source code
COPY . .

# Build the Angular app
RUN ["npm", "run", "build"]

# Stage 2: Serve the application with Nginx
FROM nginx:1.17.1-alpine

# Copy built Angular app from previous stage
COPY --from=build /app/dist/coveragex-task /usr/share/nginx/html

# Copy custom nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
