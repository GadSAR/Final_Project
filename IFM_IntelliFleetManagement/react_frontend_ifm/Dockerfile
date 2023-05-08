
FROM node:18.14.2-alpine

COPY package.json ./

RUN npm install --force

COPY . .

EXPOSE 5173

CMD ["npm", "run", "dev"]