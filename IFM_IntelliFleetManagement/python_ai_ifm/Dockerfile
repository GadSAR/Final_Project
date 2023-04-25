# FROM python:3.9-slim-buster
FROM ubuntu

WORKDIR /app

COPY requirements.txt requirements.txt

RUN apt update
RUN apt install python3-pip -y
RUN pip3 install --no-cache-dir -r requirements.txt

COPY . .

EXPOSE 5000

CMD ["python3", "app.py"]