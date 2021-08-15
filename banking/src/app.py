import json
from flask import Flask, abort, request, make_response
from werkzeug.exceptions import HTTPException
from simple_bank_korea import kb
from common import util, enum

app = Flask(__name__)

@app.get('/kb/transactions')
def get_kb_bank_transactions():
    DEFAULT_TRANSACTIONS_LOOKUP_DAYS = 1

    account = request.args.get('account', default='')
    birthdate = request.args.get('birthdate', default='')
    password = request.args.get('password', default='')
    days = request.args.get('days', type=int, default=DEFAULT_TRANSACTIONS_LOOKUP_DAYS)

    if len(account) == 0:
        abort(400)

    if len(birthdate) == 0:
        abort(400)

    if len(password) == 0:
        abort(400)

    if days < 1:
        abort(400)

    transactions = kb.get_transactions(account, birthdate, password, days)
    response_data = {
        'data': transactions
    }
    response_data_json = json.dumps(response_data, default=util.json_convert_default)

    response = make_response(response_data_json)
    response.mimetype = enum.CONTENT_TYPE_JSON
    return response

@app.errorhandler(HTTPException)
def handle_http_exception(error):
    response = error.get_response()
    response.data = json.dumps({
        'code': error.code,
        'name': error.name,
        'description': error.description,
    })
    response.mimetype = enum.CONTENT_TYPE_JSON
    return response

@app.errorhandler(Exception)
def handle_global_exception(error):
    if isinstance(error, HTTPException):
        return error

    response = make_response(json.dumps({
        'code': -1,
        'name': 'Unknown Error',
        'description': str(error),
    }), 500)
    response.mimetype = enum.CONTENT_TYPE_JSON
    return response

if __name__ == '__main__':
    app.run(port=5000, debug=True)
