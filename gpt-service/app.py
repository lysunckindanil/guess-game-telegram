from flask import Flask
from flask import request
from flask import jsonify
from queries import *
rest_port = 8050
import py_eureka_client.eureka_client as eureka_client
eureka_client.init(eureka_server="http://localhost:8761/eureka",
                   app_name="gpt-service",
                   instance_port=rest_port)
app = Flask(__name__)


@app.route('/api/guess-word')
def guess_word_req():
    return guess_word(request.args.get('word'), request.args.get("question"))


@app.route('/api/topics/choose')
def choose_topic_req():
    response = choose_topic()
    return jsonify({"topic": response[0], "word": response[1]})


@app.route('/api/topics/used')
def used_topics_req():
    return topics_used


if __name__ == "__main__":
    app.run(host='0.0.0.0', port = rest_port)
