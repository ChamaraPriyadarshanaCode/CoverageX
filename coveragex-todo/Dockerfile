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

RUN npm run build --prod

# Run stage
FROM nginx:alpine
# Remove default files
RUN rm -rf /usr/share/nginx/html/*
# Copy built Angular files using specific outputPath
COPY --from=build /app/dist/coveragex-task/browser /usr/share/nginx/html
# Copy Nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf
# Fix permissions
RUN chmod -R 755 /usr/share/nginx/html && \
    chown -R nginx:nginx /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

