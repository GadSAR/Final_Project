from src.python.controller.rest_controller import RestAPI

app = RestAPI().app

if __name__ == '__main__':
    app.run()
