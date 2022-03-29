get all
curl --user "admin" http://localhost:8080/topjava/rest/admin/users

get user 100001
curl --user "admin" http://localhost:8080//topjava/rest/admin/users/100001

delete meal 100001
curl --user "admin" http://localhost:8080/topjava/rest/admin/meals/100001

get filtered meals
curl --user "user  http://localhost:8080/topjava/rest/profile/filter?startDate=2020-01-30&endDate=2020-01-31&startTime=01%3A00&endTime=15%3A00