FROM ubuntu

WORKDIR /app

COPY requirements.txt requirements.txt

RUN apt-get update && apt-get install -y libmysqlclient-dev && \
    pip3 install --no-cache-dir -r requirements.txt

COPY . .

EXPOSE 5000

CMD [ "flask", "run", "--host", "0.0.0.0", "--port", "5000", "--app", "src.app" ]