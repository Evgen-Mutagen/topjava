**get all meals**
curl s http://localhost:8080/topjava/rest/admin/users --user "admin"

**get user 100001**
curl s http://localhost:8080//topjava/rest/admin/users/100001 --user "admin"

**get all meals**
curl -s http://localhost:8080/topjava/rest/profile/meals --user "user"

**delete meal 100001**
curl s http://localhost:8080/topjava/rest/admin/meals/100001 --user "admin"

**get meal not found**
curl -s -v http://localhost:8080/topjava/rest/profile/meals/100020 --user "user"

**get filtered meals**
curl s http://localhost:8080/topjava/rest/profile/filter?startDate=2020-01-30&endDate=2020-01-31&startTime=01%3A00&endTime=15%3A00 --user "user" 