# Use an official Node.js runtime as a parent image
FROM node:16-alpine

# Set the working directory to /app
WORKDIR /app

# Copy package.json and pnpm-lock.yaml to the container
COPY package.json pnpm-lock.yaml /app/

# Install pnpm globally (if not already installed)
RUN npm install -g pnpm

# Install project dependencies using pnpm
RUN pnpm install

# Copy the rest of your application code to the container
COPY . /app

# Build the SvelteKit project
RUN pnpm build

# Expose a port (optional)
# EXPOSE 5173

# Specify the command to run your application
CMD ["pnpm", "dev", "--host"]

# (Optional) Set environment variables if needed
# ENV NODE_ENV production

# (Optional) Add other configuration or setup steps as needed

# (Optional) Healthcheck to verify the container's health
# HEALTHCHECK CMD curl --fail http://localhost:3000 || exit 1