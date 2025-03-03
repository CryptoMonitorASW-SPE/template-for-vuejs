# Stage 1: Build the Vue.js application
FROM node:22.13-alpine AS build

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY app/package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY app/ .

# Build the application
RUN npm run build

# Stage 2: Serve the application with Nginx
FROM nginx:stable AS prod-stage

# Copy the built files from the previous stage
COPY --from=build /app/dist /usr/share/nginx/html

# Copy nginx configuration directly to the proper location
COPY ./nginx.conf /etc/nginx/nginx.conf

# Expose port 80
EXPOSE 80

# Run nginx directly
CMD ["nginx", "-g", "daemon off;"]