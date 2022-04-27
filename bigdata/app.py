from flask import Flask
from flask_cors import CORS
from recomm import vulnerability, user_vulnerability
from mongo import mongotest

app = Flask(__name__)
CORS(app)


@app.route("/")
def hello_world():
    return "<p>Hello, World!</p>"

# @app.route('/vulnerability/<userid>')


@app.route('/user/vulnerability')
def user_vul():
    res = user_vulnerability.user_vulnerability()
    return res

# @app.route('/recomm/<userid>/vulnerability')


@app.route('/recomm/vulnerability')
def recomm_vul():
    res = vulnerability.recomm_vulnerability()
    return res


@app.route('/mongo')
def mongo():
    res = mongotest.test_query()
    return res


if __name__ == '__main__':
    app.debug = True  # 개발자 모드
    app.run()

# python app.py 혹은 flask run으로 실행
# flask run의 경우 개발자모드 비활성화
