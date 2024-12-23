This http://localhost:8080/3 api will call other api "go-httpbin/delay/3" (https://github.com/mccutchen/go-httpbin)
which always return response with 3sec delay.

Executed the tests with/without virtual threads enabled. (Macbook Pro, 2.6GHz, 6-Core)

Conclusion: Winner virtual threads

spring.threads.virtual.enabled=false
$ oha -c 100 -n 1000 http://localhost:8080/3
Summary:
Success rate:	100.00%
Total:	252.9464 secs
Slowest:	30.1154 secs
Fastest:	3.0087 secs
Average:	23.9938 secs
Requests/sec:	3.9534

Total data:	587.89 KiB
Size/request:	602 B
Size/sec:	2.32 KiB

spring.threads.virtual.enabled=true
$ oha -c 100 -n 1000 http://localhost:8080/3
Summary:
Success rate:	100.00%
Total:	30.8850 secs
Slowest:	3.5855 secs
Fastest:	3.0058 secs
Average:	3.0791 secs
Requests/sec:	32.3782

Total data:	587.89 KiB
Size/request:	602 B
Size/sec:	19.03 KiB


