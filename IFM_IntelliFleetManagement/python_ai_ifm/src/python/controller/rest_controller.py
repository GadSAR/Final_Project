from flask import Flask, request, abort, render_template, jsonify
from flask_cors import CORS

from src.python.models.model1 import Model1
from src.python.models.model2 import Model2
from src.python.models.model3 import Model3
from src.python.services.data_service import DataService


class RestAPI:
    def __init__(self):
        self.app = Flask(__name__, template_folder='../../resources/templates')
        CORS(self.app)
        self.model1 = None
        self.model2 = None
        self.model3 = None
        self.data_service = DataService()

        @self.app.before_request
        def restrict_access():
            ALLOWED_PORTS = [3000, 5173, 8080, 5000]
            remote_port = request.environ.get('REMOTE_PORT')

            if remote_port not in ALLOWED_PORTS:
                ALLOWED_PORTS.append(remote_port)

            if remote_port not in ALLOWED_PORTS:
                abort(403)

        @self.app.route('/')
        def home():
            return render_template('index.html')

        @self.app.route('/check')
        def check_api():
            # Load the dataset
            data = self.data_service.get_data()
            self.init_models()
            self.model1.model1_check(data)
            self.model2.model2_check(data)
            self.model3.model3(data)
            return jsonify({'message': f'prediction worked'})

        @self.app.route('/predict_car')
        def model1_predict_all():
            car_id = request.args.get('carId')
            data = self.data_service.get_data()
            predict_data = self.data_service.get_last_car_data(car_id)
            self.init_models()
            is_issue = self.model1.model1_prediction(predict_data)
            if is_issue == 1:
                trouble_code = self.model2.model2_prediction(predict_data, data)
                next_issue = self.model3.model3_prediction(predict_data, data)
                return jsonify({'is_issue': is_issue, 'trouble_code': trouble_code, 'next_issue': next_issue})
            return jsonify({'is_issue': is_issue, 'trouble_code': "---", 'next_issue': "---"})

        @self.app.route('/rebuild_models')
        def models_rebuild():
            data = self.data_service.get_data()
            self.init_models()
            self.model1.model1_build(data)
            self.model2.model2_build(data)
            return jsonify({'message': f'Started rebuilding models'})

        @self.app.route('/rebuild_model1')
        def model1_rebuild():
            data = self.data_service.get_data()
            self.init_models()
            self.model1.model1_build(data)
            return jsonify({'message': f'Started rebuilding model1'})

        @self.app.route('/rebuild_model2')
        def model2_rebuild():
            data = self.data_service.get_data()
            self.init_models()
            self.model2.model2_build(data)
            return jsonify({'message': f'Started rebuilding model2'})

    def init_models(self):
        self.model1 = Model1()
        self.model2 = Model2()
        self.model3 = Model3()

    def run(self):
        self.app.run()


app = RestAPI().app
