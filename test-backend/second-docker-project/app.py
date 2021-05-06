from flask import Flask
from flask_cors import CORS


app = Flask(__name__)
CORS(app)

@app.route('/helloworld', methods=['GET'])
def helloworld():
    return {'message': "Hello World, from flask"}



if __name__ == '__main__':
    app.run(host="0.0.0.0")