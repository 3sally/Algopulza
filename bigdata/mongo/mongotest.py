from mongo import mongoclass


def test_query():

    # connect
    member_repo = mongoclass.MongoRepository()

    # set db, collection
    member_repo.database = "testcompass"
    member_repo.collection = "testcoll"

    # member = member_repo.get_or_create_member(name='제이')
    # print(member)
    # member = member_repo.update_job(member.get('_id'), '프론트엔드 개발자')
    # print(member_repo.get_or_create_member(name='제이'))

    # set query
    wine = member_repo.find_one({"country": "France"})

    # close connect
    member_repo.close()

    return wine
