from mongo import mongoclass


def test_query():
    member_repo = mongoclass.MongoRepository()

    member_repo.database = "testcompass"
    member_repo.collection = "testcoll"

    # member = member_repo.get_or_create_member(name='제이')
    # print(member)
    # member = member_repo.update_job(member.get('_id'), '프론트엔드 개발자')
    # print(member_repo.get_or_create_member(name='제이'))

    wine = member_repo.find_one({"country": "France"})
    return wine
