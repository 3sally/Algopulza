from pydoc import cli
from pymongo import MongoClient
from sshtunnel import SSHTunnelForwarder
import paramiko
import io
import base64
with open('./mongo/K6A408T.pem', 'rb') as f:
    blob = base64.b64encode(f.read())


class MongoRepository:

    def __init__(self, conn=None, database_name=None, collection_name=None):
        self._conn = self._get_connection() if not conn else conn
        self._database_name = database_name
        self._collection_name = collection_name
        self._serv

    # @staticmethod
    def _get_connection(self):
        # MONGO_HOST = 'k6a4081.p.ssafy.io:22'
        # SERVER_USER = 'ubuntu'
        # PRIVATE_KEY = 'PATH ./mongo/K6A408T.pem'

        # key = open('./mongo/K6A408T.pem', 'r')
        # key = open('./mongo/K6A408T.pem', 'r')
        # PRIVATE_KEY = key

        # # define ssh tunnel
        # server = SSHTunnelForwarder(
        #     MONGO_HOST,
        #     ssh_username=SERVER_USER,
        #     ssh_pkey=PRIVATE_KEY,
        #     ssh_password=None,
        #     remote_bind_address=('localhost', 27017)
        # )

        remote_user = 'ubuntu'
        remote_host = 'k6a4081.p.ssafy.io'
        remote_port = 22
        local_host = '0.0.0.0'
        local_port = 27017
        # pemkey = 'PATH ./mongo/K6A408T.pem'
        # pemkey = open('./mongo/K6A408T.pem', 'r')
        for_conn_function = blob.decode('utf-8')
        SSH_KEY_BLOB_DECODED = base64.b64decode(for_conn_function)
        SSH_KEY = SSH_KEY_BLOB_DECODED.decode('utf-8')
        pemkey = paramiko.RSAKey.from_private_key(io.StringIO(SSH_KEY))
        print("------------------")
        print("hk")
        print("------------------")
        print(blob)
        print("------------------")
        print(for_conn_function)
        # print("------------------")
        # print(pemkey.seek)
        # print("------------------")
        # print(pemkey.read)
        # print("------------------")
        # print(pemkey.readline)
        # print("------------------")

        server = SSHTunnelForwarder(
            (remote_host, remote_port),
            ssh_username=remote_user,
            ssh_private_key=pemkey,
            remote_bind_address=(local_host, local_port),
            local_bind_address=(local_host, local_port),
        )

        # start ssh tunnel
        server.start()

        self._serv = server

        conn = MongoClient('0.0.0.0', server.local_bind_port)

        return conn

    # SSH connect with paramiko
    # def _get_connection(self):

    #     client = paramiko.SSHClient()
    #     client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    #     client.connect('0.0.0.0', username='ubuntu', password=None,
    #                    key_filename='./mongo/K6A408T.pem')

    #     self._serv = client

    #     conn = MongoClient('0.0.0.0', server.local_bind_port)

    #     return client

    # make new get_connection method here (to use for ec2)
    # def _get_connection(self):

    # @staticmethod

    def connect(self):
        self._server.start()
        self.local_port = self._server.local_bind_port

    def close(self):
        self._server.stop()

    def __enter__(self):
        self.connect()
        return self

    def __exit__(self, type, value, traceback):
        self.close()

    @property
    def database(self):
        return self._conn[self._database_name]

    @property
    def collection(self):
        return self.database[self._collection_name]

    def insert_one(self, data: dict):
        if not data:
            return
        return self.collection.insert_one(data)

    def find_one(self, _filter: dict):
        if not _filter:
            return None

        return self.collection.find_one(_filter)

    def update_one(self, _filter: dict, update_data: dict):
        if not update_data:
            return

        return self.collection.update_one(_filter, update_data)
