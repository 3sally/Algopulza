from pydoc import cli
from pymongo import MongoClient
from sshtunnel import SSHTunnelForwarder
import paramiko


class MongoRepository:

    def __init__(self, conn=None, database_name=None, collection_name=None):
        self._conn = self._get_connection() if not conn else conn
        self._database_name = database_name
        self._collection_name = collection_name
        self._serv

    # @staticmethod
    def _get_connection(self):
        MONGO_HOST = 'ubuntu@k6a4081.p.ssafy.io:22'
        SERVER_USER = 'ubuntu'

        # key = open('./mongo/K6A408T.pem', 'r')
        key = open('./mongo/K6A408T.txt', 'r')

        PRIVATE_KEY = key

        # define ssh tunnel
        server = SSHTunnelForwarder(
            MONGO_HOST,
            # ssh_username=SERVER_USER,
            ssh_pkey=PRIVATE_KEY,
            ssh_password=None,
            remote_bind_address=('localhost', 27017)
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
