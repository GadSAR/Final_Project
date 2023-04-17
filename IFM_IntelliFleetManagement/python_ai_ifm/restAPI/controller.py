from flask import Flask, request, jsonify, render_template, abort
from scripts import model1_predict, model1_build, model2_predict, model2_build, model3_predict


app = Flask(__name__)

# Define a list of allowed IP addresses and ports
ALLOWED_IPS = ['127.0.0.1', 'localhost']
ALLOWED_PORTS = [3000, 5173, 8080, 5000]


# Define a function to validate incoming requests
@app.before_request
def restrict_access():
    remote_ip = request.remote_addr
    remote_port = request.environ.get('REMOTE_PORT')

    if remote_ip not in ALLOWED_IPS:
        ALLOWED_IPS.append(remote_ip)

    if remote_port not in ALLOWED_PORTS:
        ALLOWED_PORTS.append(remote_port)

    if remote_ip not in ALLOWED_IPS:
        abort(403)

    if remote_port not in ALLOWED_PORTS:
        abort(403)


@app.route('/')
def home():
    return render_template('index.html')


# Define a route for GET request
@app.route('/get_example', methods=['GET'])
def get_example():
    # Your code for handling the GET request
    print('hello')
    return jsonify({'message': 'This is a GET request example'})


# Define a route for POST request
@app.route('/post_example', methods=['POST'])
def post_example():
    # Your code for handling the POST request
    data = request.get_json()  # Get input data from request
    # Process the input data and generate a response
    response = {'message': 'This is a POST request example', 'data': data}
    return jsonify(response)


# Define a route for PUT request
@app.route('/put_example', methods=['PUT'])
def put_example():
    # Your code for handling the PUT request
    data = request.get_json()  # Get input data from request
    # Process the input data and generate a response
    response = {'message': 'This is a PUT request example', 'data': data}
    return jsonify(response)


# Define a route for DELETE request
@app.route('/delete_example', methods=['DELETE'])
def delete_example():
    # Your code for handling the DELETE request
    return jsonify({'message': 'This is a DELETE request example'})


# Define a route with a parameter in the URL
@app.route('/domain/<int:domain_id>', methods=['GET'])
def get_domain(domain_id):
    # Your code for handling the GET request with the domain ID parameter
    return jsonify({'message': f'This is domain ID {domain_id}'})


if __name__ == '__main__':
    app.run(host='localhost', port=5000, debug=True)
