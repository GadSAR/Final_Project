
FROM node:18.14.2-alpine

WORKDIR /app

COPY package.json package.json

RUN npm install --force

COPY . .

EXPOSE 5173

CMD ["npm", "run", "dev"]