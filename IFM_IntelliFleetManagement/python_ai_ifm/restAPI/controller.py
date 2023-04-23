from flask import Flask, request, jsonify, abort, render_template
from concurrent.futures import ThreadPoolExecutor
from flask_cors import CORS

from models.methods import get_data
from models.model1 import model1_check
from models.model2 import model2_check
from models.model3 import model3
from scripts import model1_predict, model1_build, model2_predict, model2_build, model3_predict

app = Flask(__name__)
CORS(app)

# Define a list of allowed IP addresses and ports
ALLOWED_PORTS = [3000, 5173, 8080, 5000]

# Define the maximum number of threads to use for each model
MAX_THREADS = 5

# Define a thread pool executor for each model
model1_executor = ThreadPoolExecutor(max_workers=MAX_THREADS)
model2_executor = ThreadPoolExecutor(max_workers=MAX_THREADS)
model3_executor = ThreadPoolExecutor(max_workers=MAX_THREADS)


# Define a function to validate incoming requests
@app.before_request
def restrict_access():
    remote_ip = request.remote_addr
    remote_port = request.environ.get('REMOTE_PORT')

    if remote_port not in ALLOWED_PORTS:
        ALLOWED_PORTS.append(remote_port)

    if remote_port not in ALLOWED_PORTS:
        abort(403)


@app.route('/')
def home():
    return render_template('index.html')


# Define the routes for each model
@app.route('/model1/build', methods=['POST'])
def model1_build_api():
    model_id = request.json.get('model_id')
    future = model1_executor.submit(model1_build, model_id)
    return jsonify({'message': f'Started building model 1 with ID {model_id}'})


@app.route('/model1/predict')
def model1_predict_api():
    # Load the dataset
    data = get_data()
    model1_executor.submit(model1_check, data)
    model2_executor.submit(model2_check, data)
    model3_executor.submit(model3, data)
    return jsonify({'message': f'Started predicting with model 1'})


@app.route('/model2/build', methods=['POST'])
def model2_build_api():
    model_id = request.json.get('model_id')
    future = model2_executor.submit(model2_build, model_id)
    return jsonify({'message': f'Started building model 2 with ID {model_id}'})


@app.route('/model2/predict', methods=['POST'])
def model2_predict_api():
    # Load the dataset
    data = get_data()
    future = model2_executor.submit(model2_predict, data)
    return jsonify({'message': f'Started predicting with model 2'})


@app.route('/model3/predict', methods=['POST'])
def model3_predict_api():
    # Load the dataset
    data = get_data()
    future = model3_executor.submit(model3, data)
    return jsonify({'message': 'Started predicting with model 3'})


if __name__ == '__main__':
    app.run(debug=True)